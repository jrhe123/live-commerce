package org.example.live.id.generate.provider.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.example.live.id.generate.provider.dao.mapper.IdGenerateMapper;
import org.example.live.id.generate.provider.dao.po.IdGeneratePO;
import org.example.live.id.generate.provider.service.IdGenerateService;
import org.example.live.id.generate.provider.service.bo.LocalSeqIdBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.alibaba.nacos.shaded.io.grpc.netty.shaded.io.netty.util.internal.ThreadLocalRandom;

import ch.qos.logback.core.status.Status;
import jakarta.annotation.Resource;
import javassist.expr.NewArray;

@Service
public class IdGenerateServiceImpl implements IdGenerateService, InitializingBean {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IdGenerateServiceImpl.class); 
	private static Map<Integer, LocalSeqIdBO> localSeqIdMap = new ConcurrentHashMap<>();
	private static final float UPDATE_RATE = 0.75f;
	private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			8, 16, 3, TimeUnit.SECONDS,
			new ArrayBlockingQueue<>(1000),
			new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread thread = new Thread(r);
				thread.setName("id-generate-thread-" + ThreadLocalRandom.current().nextInt(1000));
				return thread;
			}
	});
	private static Map<Integer, Semaphore> semaphoreMap = new ConcurrentHashMap<Integer, Semaphore>();
	
	@Resource
	private IdGenerateMapper idGenerateMapper;
	
	
	// when bean initializing -> callback
	@Override
	public void afterPropertiesSet() throws Exception {
		List<IdGeneratePO> selectAll = idGenerateMapper.selectAll();
		for (IdGeneratePO idGeneratePO: selectAll) {
			//
			tryUpdateMySQLRecrod(idGeneratePO);
			
			semaphoreMap.put(idGeneratePO.getId(), new Semaphore(1));
		}
	}
	
	private void tryUpdateMySQLRecrod(IdGeneratePO idGeneratePO) {
		long currentStart = idGeneratePO.getCurrentStart();
		long nextThreshold = idGeneratePO.getNextThreshold();
		long currentNum = currentStart;
		
		// update id count & version
		int updateResult = idGenerateMapper.updateNewIdCountAndVersion(
							idGeneratePO.getId(), idGeneratePO.getVersion());
		if (updateResult > 0) {
			LocalSeqIdBO localSeqIdBO = new LocalSeqIdBO();
			localSeqIdBO.setId(idGeneratePO.getId());
			AtomicLong atomicLong = new AtomicLong(currentNum);
			localSeqIdBO.setCurrentNum(atomicLong);
			localSeqIdBO.setCurrentStart(currentStart);
			localSeqIdBO.setNextThreshold(nextThreshold);
			
			localSeqIdMap.put(localSeqIdBO.getId(), localSeqIdBO);
			return;
		}
		// if update is failed (b/c multi-thread trying to update it)
		// we will re-try now
		for (int i = 0; i < 3; i++) {
			idGeneratePO = idGenerateMapper.selectById(idGeneratePO.getId());
			updateResult = idGenerateMapper.updateNewIdCountAndVersion(
					idGeneratePO.getId(), idGeneratePO.getVersion());
			if (updateResult > 0) {
				LocalSeqIdBO localSeqIdBO = new LocalSeqIdBO();
				localSeqIdBO.setId(idGeneratePO.getId());
				AtomicLong atomicLong = new AtomicLong(idGeneratePO.getCurrentStart());
				localSeqIdBO.setCurrentNum(atomicLong);
				localSeqIdBO.setCurrentStart(idGeneratePO.getCurrentStart());
				localSeqIdBO.setNextThreshold(idGeneratePO.getNextThreshold());
				
				localSeqIdMap.put(localSeqIdBO.getId(), localSeqIdBO);
				return;
			}
		}
		
		throw new RuntimeException("id generate failed: " + idGeneratePO.getId());
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
		 * init	  threshold  step	version
		 * 10000   10050      50	   1
		 * 10050   10100      50	   2		after 50 ids used
		 * 10100   10150      50	   3		after 100 ids used
		 */
		
		
		/**
		 * machine A 
		 * 
		 * v1		v3
		 * 10000	10100
		 * 10050	10150
		 * 
		 * machine B
		 * 
		 * v2		v4
		 * 10050	10150
		 * 10100	10200
		 */
		
		// check the UPDATE_RATE, if we need to refresh the RAM
		// e.g., 75%
		this.refreshLocalSeqId(localSeqIdBO);
		
		if (localSeqIdBO.getCurrentNum().get() > localSeqIdBO.getNextThreshold()) {
			LOGGER.error("[getSeqId] id is over limit, id is {}", localSeqIdBO.getId());
			return null;
		}
		
		long returnId = localSeqIdBO.getCurrentNum().getAndIncrement();
		return returnId;
	}

	private void refreshLocalSeqId(LocalSeqIdBO localSeqIdBO) {
		long step = localSeqIdBO.getNextThreshold() - localSeqIdBO.getCurrentStart();
		if (localSeqIdBO.getCurrentNum().get() - localSeqIdBO.getCurrentStart() > step * UPDATE_RATE) {
			
			Semaphore semaphore = semaphoreMap.get(localSeqIdBO.getId());
			if(semaphore == null) {
				LOGGER.error("semaphore is null, id is {}", localSeqIdBO.getId());
				return;
			}
			
			Boolean acquireStatus = semaphore.tryAcquire();
			if (acquireStatus) {
				LOGGER.info("trying to sync mysql ids");
				// async update ids
				threadPoolExecutor.execute(new Runnable() {
					@Override
					public void run() {
						IdGeneratePO idGeneratePO = idGenerateMapper.selectById(localSeqIdBO.getId());
						tryUpdateMySQLRecrod(idGeneratePO);
						
						 semaphoreMap.get(localSeqIdBO.getId()).release();
						 
						 LOGGER.info("done sync mysql ids");
					}
				});
			}
			
		}
	}

	@Override
	public Long getUnSeqId(Integer strategyId) {

		return null;
	}

	

}
