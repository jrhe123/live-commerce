package org.example.live.user.provider.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.example.live.common.interfaces.utils.ConvertBeanUtils;
import org.example.live.framework.redis.starter.key.UserProviderCacheKeyBuilder;
import org.example.live.user.contants.CacheAsyncDeleteCode;
import org.example.live.user.contants.UserProviderTopicNames;
import org.example.live.user.dto.UserCacheAsyncDeleteDTO;
import org.example.live.user.dto.UserDTO;
import org.example.live.user.provider.dao.mapper.IUserMapper;
import org.example.live.user.provider.dao.po.UserPO;
import org.example.live.user.provider.service.IUserService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.shaded.io.grpc.netty.shaded.io.netty.util.internal.ThreadLocalRandom;
import com.google.common.collect.Maps;

import jakarta.annotation.Resource;

@Service
public class UserServiceImpl implements IUserService {

	@Resource
	private IUserMapper userMapper;
	
	@Resource
	private RedisTemplate<String, UserDTO> redisTemplate;
	
	@Resource
	private UserProviderCacheKeyBuilder userProviderCacheKeyBuilder;
	
	@Resource
	private MQProducer mqProducer;

	
	@Override
	public UserDTO getByUserId(Long userId) {
		if (userId == null) {
			return null;
		}
		// redis
		// String keyString = "userInfo:" + userId;
		String keyString = userProviderCacheKeyBuilder.buildUserInfoKey(userId);
		UserDTO userDTO = redisTemplate.opsForValue().get(keyString);
		if (userDTO != null) return userDTO;
		
		// mysql
		userDTO = ConvertBeanUtils.convert(
				userMapper.selectById(userId), UserDTO.class);
		if (userDTO != null) {
			redisTemplate.opsForValue().set(
					keyString, userDTO, 30, TimeUnit.MINUTES);
		}
		
		return userDTO;
	}

	@Override
	public boolean updateUserInfo(UserDTO userDTO) {
		if (userDTO == null || userDTO.getUserId() == null) {
			return false;
		}
		
		// update mysql
		userMapper.updateById(
				ConvertBeanUtils.convert(userDTO, UserPO.class)
				);
		
		// delete redis
		String keyString = userProviderCacheKeyBuilder.buildUserInfoKey(userDTO.getUserId());
		redisTemplate.delete(keyString);
		
		// MQ delay delete redis
		try {
			
			Map<String, Object> jsonParam = new HashMap<>();
			jsonParam.put("userId", userDTO.getUserId());
			UserCacheAsyncDeleteDTO userCacheAsyncDeleteDTO = new UserCacheAsyncDeleteDTO();
			userCacheAsyncDeleteDTO.setCode(CacheAsyncDeleteCode.USER_INFO_DELETE.getCode());
			userCacheAsyncDeleteDTO.setJson(JSON.toJSONString(jsonParam));
			
			
			// !!! SEND MQ !!!
			Message message = new Message();
			message.setTopic(UserProviderTopicNames.CACHE_ASYNC_DELETE_TOPIC);
			message.setBody(JSON.toJSONString(userCacheAsyncDeleteDTO).getBytes());
			message.setDelayTimeLevel(1); // delay 1 sec
			mqProducer.send(message);
		} catch (MQClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemotingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MQBrokerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}

	@Override
	public boolean insertOne(UserDTO userDTO) {
		if (userDTO == null || userDTO.getUserId() == null) {
			return false;
		}
		
		UserPO userPO = ConvertBeanUtils.convert(userDTO, UserPO.class);
		userMapper.insert(userPO);
		return true;
	}

	@Override
	public Map<Long, UserDTO> batchQueryUserInfo(List<Long> userIdList) {
		if (CollectionUtils.isEmpty(userIdList)) {
			return Maps.newHashMap();
		}
		
		userIdList = userIdList.stream().filter(
				id -> id > 10000).collect(Collectors.toList());
		if (CollectionUtils.isEmpty(userIdList)) {
			return Maps.newHashMap();
		}
		
		// redis
		/**
		 * v1
		 * redis get for each userId
		 * 
		 * userIdList.forEach(userId -> {
		 *   redisTemplate.opsForValue().get(userId);
		 * });
		 */
		/**
		 * v2
		 * use redis multiGet()
		 * 
		 * one I/O instead of multiple search
		 */
		// 1. list we search
		List<String> keyList = new ArrayList<>();
		userIdList.forEach(userId -> {
			keyList.add(
					userProviderCacheKeyBuilder.buildUserInfoKey(userId)
					);
		});
		
		// 2. cached list of userDTO
		List<UserDTO> userDTOList = redisTemplate.opsForValue()
				.multiGet(keyList).stream().filter(x -> x!=null)
				.collect(Collectors.toList());
		
		// 2.1 everything is cached
		if (!CollectionUtils.isEmpty(userDTOList) &&
				userDTOList.size() == userIdList.size()
				) {
			Map<Long, UserDTO> resultMap = userDTOList.stream().collect(
					Collectors.toMap(UserDTO::getUserId, x -> x)
					);
			return resultMap;
		}
			
		// 3. convert to list of id
		List<Long> userIdInCacheList = userDTOList.stream()
				.map(UserDTO::getUserId)
				.collect(Collectors.toList());
		
		// 4. list of id not cached, then we search them in mysql
		List<Long> userIdNotInCacheList = userIdList.stream()
				.filter(x->!userIdInCacheList.contains(x))
				.collect(Collectors.toList());
		
		
		// mysql
		/**
		 * v1
		 * bad performance, "union all"
		 * 
		 * userMapper.selectBatchIds(userIdList);
		 */
		
		/**
		 * v2
		 * let's use multi-threads here, we mod 100 (%100) every 100 per table
		 * each thread batch search list of userId, which is in SAME table
		 */
		Map<Long, List<Long>> userIdMap = userIdNotInCacheList.stream().collect(
				Collectors.groupingBy(userId -> userId%100)
				);
		
		List<UserDTO> dbQueryResult = new CopyOnWriteArrayList<>();
		userIdMap.values().parallelStream().forEach(queryUserIdList -> {
			List<UserDTO> eachList = 
					ConvertBeanUtils.convertList(
							userMapper.selectBatchIds(queryUserIdList), UserDTO.class);
					
			dbQueryResult.addAll(eachList);
		});
		
		// 5. cache mysql result back to redis
		if (!CollectionUtils.isEmpty(dbQueryResult)) {
			Map<String, UserDTO> saveCacheMap = dbQueryResult.stream()
					.collect(Collectors.toMap(
							userDTO->userProviderCacheKeyBuilder.buildUserInfoKey(userDTO.getUserId()), 
							x->x
							));
			// multiset
			redisTemplate.opsForValue().multiSet(saveCacheMap);
			
			// reduce IO, batch set expire time
			redisTemplate.executePipelined(new SessionCallback<Object>() {
				@Override
				public <K, V> Object execute(RedisOperations<K, V> operations) 
						throws DataAccessException {
					
					for(String redisKey: saveCacheMap.keySet()) {
						// why we use random expire time?
						// try to avoid expiration for a big batch of data
						// pressure back to mysql
						operations.expire((K) redisKey, 
								createRandomExpireTime(), TimeUnit.SECONDS);
					}
					return null;
				}
			});
		}
				
		// 6. merge the redis & mysql result
		userDTOList.addAll(dbQueryResult);
		// 7. result
		Map<Long, UserDTO> resultMap = userDTOList.stream().collect(
				Collectors.toMap(UserDTO::getUserId, x -> x)
				);
		return resultMap;
	}
	
	private int createRandomExpireTime() {
		int time = ThreadLocalRandom.current().nextInt(1000);
		return time + 60 * 30;
	}
}











