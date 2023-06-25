package org.example.live.id.generate.provider.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.example.live.id.generate.provider.dao.mapper.IdGenerateMapper;
import org.example.live.id.generate.provider.dao.po.IdGeneratePO;
import org.example.live.id.generate.provider.service.IdGenerateService;
import org.example.live.id.generate.provider.service.bo.LocalSeqIdBO;
import org.example.live.id.generate.provider.service.bo.LocalUnSeqIdBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.alibaba.nacos.shaded.io.grpc.netty.shaded.io.netty.util.internal.ThreadLocalRandom;

import jakarta.annotation.Resource;

@Service
public class IdGenerateServiceImpl implements IdGenerateService, InitializingBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(IdGenerateServiceImpl.class);
	private static Map<Integer, LocalSeqIdBO> localSeqIdMap = new ConcurrentHashMap<>();
	private static final float UPDATE_RATE = 0.75f;
	private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(8, 16, 3, TimeUnit.SECONDS,
			new ArrayBlockingQueue<>(1000), new ThreadFactory() {
				@Override
				public Thread newThread(Runnable r) {
					Thread thread = new Thread(r);
					thread.setName("id-generate-thread-" + ThreadLocalRandom.current().nextInt(1000));
					return thread;
				}
			});
	private static Map<Integer, Semaphore> semaphoreMap = new ConcurrentHashMap<Integer, Semaphore>();
	private static final int SEQ_ID = 1;

	private static Map<Integer, LocalUnSeqIdBO> localUnSeqIdMap = new ConcurrentHashMap<>();

	@Resource
	private IdGenerateMapper idGenerateMapper;

	// when bean initializing -> callback
	@Override
	public void afterPropertiesSet() throws Exception {
		List<IdGeneratePO> selectAll = idGenerateMapper.selectAll();
		for (IdGeneratePO idGeneratePO : selectAll) {
			//
			tryUpdateMySQLRecrod(idGeneratePO);

			semaphoreMap.put(idGeneratePO.getId(), new Semaphore(1));
		}
	}

	private void tryUpdateMySQLRecrod(IdGeneratePO idGeneratePO) {
		// update id count & version
		int updateResult = idGenerateMapper.updateNewIdCountAndVersion(idGeneratePO.getId(), idGeneratePO.getVersion());
		if (updateResult > 0) {
			localIdBOHandler(idGeneratePO);
			return;
		}
		// if update is failed (b/c multi-thread trying to update it)
		// we will re-try now
		for (int i = 0; i < 3; i++) {
			idGeneratePO = idGenerateMapper.selectById(idGeneratePO.getId());
			updateResult = idGenerateMapper.updateNewIdCountAndVersion(idGeneratePO.getId(), idGeneratePO.getVersion());
			if (updateResult > 0) {
				localIdBOHandler(idGeneratePO);
				return;
			}
		}

		throw new RuntimeException("id generate failed: " + idGeneratePO.getId());
	}

	private void localIdBOHandler(IdGeneratePO idGeneratePO) {
		long currentStart = idGeneratePO.getCurrentStart();
		long nextThreshold = idGeneratePO.getNextThreshold();
		long currentNum = currentStart;

		if (idGeneratePO.getIsSeq() == SEQ_ID) {
			LocalSeqIdBO localSeqIdBO = new LocalSeqIdBO();
			localSeqIdBO.setId(idGeneratePO.getId());
			AtomicLong atomicLong = new AtomicLong(currentNum);
			localSeqIdBO.setCurrentNum(atomicLong);
			localSeqIdBO.setCurrentStart(currentStart);
			localSeqIdBO.setNextThreshold(nextThreshold);
			localSeqIdMap.put(localSeqIdBO.getId(), localSeqIdBO);
		} else {
			LocalUnSeqIdBO localUnSeqIdBO = new LocalUnSeqIdBO();
			localUnSeqIdBO.setId(idGeneratePO.getId());
			localUnSeqIdBO.setCurrentStart(currentStart);
			localUnSeqIdBO.setNextThreshold(nextThreshold);

			ConcurrentLinkedQueue<Long> idQueue = new ConcurrentLinkedQueue<>();
			long begin = localUnSeqIdBO.getCurrentStart();
			long end = localUnSeqIdBO.getNextThreshold();
			List<Long> idList = new ArrayList<>();
			for (long i = begin; i < end; i++) {
				idList.add(i);
			}
			Collections.shuffle(idList);
			idQueue.addAll(idList);
			localUnSeqIdBO.setIdQueue(idQueue);
			localUnSeqIdMap.put(localUnSeqIdBO.getId(), localUnSeqIdBO);
		}
	}

	@Override
	public Long getUnSeqId(Integer strategyId) {
		if (strategyId == null) {
			LOGGER.error("[getUnSeqId] strategyId error: {}", strategyId);
			return null;
		}

		LocalUnSeqIdBO localUnSeqIdBO = localUnSeqIdMap.get(strategyId);

		if (localUnSeqIdBO == null) {
			LOGGER.error("[getUnSeqId] localUnSeqIdBO error: {}", strategyId);
			return null;
		}

		Long returnId = localUnSeqIdBO.getIdQueue().poll();

		if (returnId == null) {
			LOGGER.error("[getUnSeqId] returnId is null: {}", strategyId);
			return null;
		}

		// check the UPDATE_RATE, if we need to refresh the RAM
		// e.g., 75%
		this.refreshLocalUnSeqId(localUnSeqIdBO);

		return returnId;
	}

	@Override
	public Long getSeqId(Integer strategyId) {
		if (strategyId == null) {
			LOGGER.error("[getSeqId] strategyId error: {}", strategyId);
			return null;
		}

		LocalSeqIdBO localSeqIdBO = localSeqIdMap.get(strategyId);
		if (localSeqIdBO == null) {
			LOGGER.error("[getSeqId] localSeqIdBO error: {}", strategyId);
			return null;
		}

		/**
		 * init threshold step version 10000 10050 50 1 10050 10100 50 2 after 50 ids
		 * used 10100 10150 50 3 after 100 ids used
		 */

		/**
		 * machine A
		 * 
		 * v1 v3 10000 10100 10050 10150
		 * 
		 * machine B
		 * 
		 * v2 v4 10050 10150 10100 10200
		 */

		// check the UPDATE_RATE, if we need to refresh the RAM
		// e.g., 75%
		this.refreshLocalSeqId(localSeqIdBO);
		
		
		long returnId = localSeqIdBO.getCurrentNum().getAndIncrement();
		
		if (returnId > localSeqIdBO.getNextThreshold()) {
			LOGGER.error("[getSeqId] id is over limit, id is {}", localSeqIdBO.getId());
			return null;
		}		
		return returnId;
	}

	private void refreshLocalSeqId(LocalSeqIdBO localSeqIdBO) {
		long step = localSeqIdBO.getNextThreshold() - localSeqIdBO.getCurrentStart();
		if (localSeqIdBO.getCurrentNum().get() - localSeqIdBO.getCurrentStart() > step * UPDATE_RATE) {

			Semaphore semaphore = semaphoreMap.get(localSeqIdBO.getId());
			if (semaphore == null) {
				LOGGER.error("semaphore is null, id is {}", localSeqIdBO.getId());
				return;
			}

			Boolean acquireStatus = semaphore.tryAcquire();
			if (acquireStatus) {
				LOGGER.info("trying to sync mysql seq ids");
				// async update ids
				threadPoolExecutor.execute(new Runnable() {
					@Override
					public void run() {
						try {
							IdGeneratePO idGeneratePO = idGenerateMapper.selectById(localSeqIdBO.getId());
							tryUpdateMySQLRecrod(idGeneratePO);

							LOGGER.info("done sync mysql seq ids");
						} catch (Exception e) {
							LOGGER.error("[refreshLocalSeqId] error id: {}", localSeqIdBO.getId());
						} finally {
							semaphoreMap.get(localSeqIdBO.getId()).release();
						}
					}
				});
			}

		}
	}

	private void refreshLocalUnSeqId(LocalUnSeqIdBO localUnSeqIdBO) {
		long begin = localUnSeqIdBO.getCurrentStart();
		long end = localUnSeqIdBO.getNextThreshold();

		long remainSize = localUnSeqIdBO.getIdQueue().size();
		if ((end - begin) * 0.25 > remainSize) {

			Semaphore semaphore = semaphoreMap.get(localUnSeqIdBO.getId());
			if (semaphore == null) {
				LOGGER.error("semaphore is null, id is {}", localUnSeqIdBO.getId());
				return;
			}

			Boolean acquireStatus = semaphore.tryAcquire();
			if (acquireStatus) {
				LOGGER.info("trying to sync mysql un-seq ids");
				// async update ids
				threadPoolExecutor.execute(new Runnable() {
					@Override
					public void run() {
						try {
							IdGeneratePO idGeneratePO = idGenerateMapper.selectById(localUnSeqIdBO.getId());
							tryUpdateMySQLRecrod(idGeneratePO);

							LOGGER.info("done sync mysql un-seq ids");
						} catch (Exception e) {
							LOGGER.error("[refreshLocalUnSeqId] error id: {}", localUnSeqIdBO.getId());
						} finally {
							semaphoreMap.get(localUnSeqIdBO.getId()).release();
						}
					}
				});
			}
		}
	}

}
