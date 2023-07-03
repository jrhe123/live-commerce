package org.example.live.user.provider.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.example.live.common.interfaces.utils.ConvertBeanUtils;
import org.example.live.framework.redis.starter.key.UserProviderCacheKeyBuilder;
import org.example.live.user.contants.CacheAsyncDeleteCode;
import org.example.live.user.contants.UserProviderTopicNames;
import org.example.live.user.contants.UserTagFieldNameConstants;
import org.example.live.user.contants.UserTagsEnum;
import org.example.live.user.dto.UserCacheAsyncDeleteDTO;
import org.example.live.user.dto.UserTagDTO;
import org.example.live.user.provider.dao.mapper.IUserTagMapper;
import org.example.live.user.provider.dao.po.UserTagPO;
import org.example.live.user.provider.service.IUserTagService;
import org.example.live.user.provider.utils.TagInfoUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import jakarta.annotation.Resource;

@Service
public class UserTagServiceImpl implements IUserTagService {
	
	@Resource
	private IUserTagMapper userTagMapper;
	
	@Resource
	private RedisTemplate<String, UserTagDTO> redisTemplate;
	
	@Resource
	private UserProviderCacheKeyBuilder userProviderCacheKeyBuilder;
	
	@Resource
	private MQProducer mqProducer;
	

	@Override
	public boolean setTag(Long userId, UserTagsEnum userTagsEnum) {
		boolean updateStatus = userTagMapper.setTag(
				userId, userTagsEnum.getFieldName(), userTagsEnum.getTag()
				)  > 0;
				
		
		if (updateStatus) {
			deleteUserTagDTOFromRedis(userId);
			
			return true;
		}
		
		UserTagPO userTagPO = userTagMapper.selectById(userId);
		
		// already exists
		if (userTagPO != null) {
			return false;
		}
		
		// We need to prevent multiple threads trying to insert the record
		// So we use redis to check
		String redisLockKey = userProviderCacheKeyBuilder.buildTagLockKey(userId);
		String setNxResult = redisTemplate.execute(new RedisCallback<String>() {

			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer keySerializer = redisTemplate.getKeySerializer();
				RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
				
				String result = (String) connection.execute(
						"set",
						keySerializer.serialize(redisLockKey),
						valueSerializer.serialize("-1"),
						"NX".getBytes(StandardCharsets.UTF_8),
						"EX".getBytes(StandardCharsets.UTF_8),
						"3".getBytes(StandardCharsets.UTF_8)
						);
				return result;
			}
		});
		if (!"OK".equals(setNxResult)) {
			return false;
		}
		
		// insert new & update
		userTagPO = new UserTagPO();
		userTagPO.setUserId(userId);
		
		userTagMapper.insert(userTagPO);
		updateStatus = userTagMapper.setTag(
				userId, userTagsEnum.getFieldName(), userTagsEnum.getTag()
				)  > 0;
		
		// release the redis lock
		redisTemplate.delete(redisLockKey);
		
		return updateStatus;
	}

	@Override
	public boolean cancelTag(Long userId, UserTagsEnum userTagsEnum) {
		boolean cancelStatus = userTagMapper.cancelTag(
				userId, userTagsEnum.getFieldName(), userTagsEnum.getTag()
				) > 0;
						
		if (!cancelStatus) {
			return false;
		}
		
		deleteUserTagDTOFromRedis(userId);
		
		return true;
	}

	@Override
	public boolean containTag(Long userId, UserTagsEnum userTagsEnum) {
//		UserTagPO userTagPO = userTagMapper.selectByUserId(userId);
		UserTagDTO userTagDTO = this.queryByUserIdFromRedis(userId);
				
		if (userTagDTO == null) {
			return false;
		}
		
		String fieldName = userTagsEnum.getFieldName();
		Long tagInfo;
		
		if (UserTagFieldNameConstants.TAG_INFO_01.equals(fieldName)) {
			tagInfo = userTagDTO.getTagInfo01();
			return TagInfoUtils.isContain(tagInfo, userTagsEnum.getTag());
		} else if (UserTagFieldNameConstants.TAG_INFO_02.equals(fieldName)) {
			tagInfo = userTagDTO.getTagInfo02();
			return TagInfoUtils.isContain(tagInfo, userTagsEnum.getTag());
		} else if (UserTagFieldNameConstants.TAG_INFO_03.equals(fieldName)) {
			tagInfo = userTagDTO.getTagInfo03();
			return TagInfoUtils.isContain(tagInfo, userTagsEnum.getTag());
		}
		
		return false;
	}
	
	
	/**
	 * delete user tag from redis
	 * 
	 * @param userId
	 */
	private void deleteUserTagDTOFromRedis(Long userId) {
		String userTagKeyString = userProviderCacheKeyBuilder.buildTagKey(userId);
		redisTemplate.delete(userTagKeyString);
		
		// mq delay delete redis
		try {
			
			Map<String, Object> jsonParam = new HashMap<>();
			jsonParam.put("userId", userId);
			UserCacheAsyncDeleteDTO userCacheAsyncDeleteDTO = new UserCacheAsyncDeleteDTO();
			userCacheAsyncDeleteDTO.setCode(CacheAsyncDeleteCode.USER_TAG_DELETE.getCode());
			userCacheAsyncDeleteDTO.setJson(JSON.toJSONString(jsonParam));
			
			
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
	}

	
	/**
	 * search user tag from redis
	 * 
	 * @param userId
	 * @return
	 */
	private UserTagDTO queryByUserIdFromRedis(Long userId) {
		String userTagKeyString = userProviderCacheKeyBuilder.buildTagKey(userId);
		UserTagDTO userTagDTO = redisTemplate.opsForValue().get(userTagKeyString);
		
		if (userTagDTO != null) {
			return userTagDTO;
		}
		
		UserTagPO userTagPO = userTagMapper.selectById(userId);
		if (userTagPO == null) {
			return null;
		}
		
		userTagDTO = ConvertBeanUtils.convert(userTagPO, UserTagDTO.class);
		// save in redis
		redisTemplate.opsForValue().set(userTagKeyString, userTagDTO);
		redisTemplate.expire(userTagKeyString, 30, TimeUnit.MINUTES);
		return userTagDTO;
	}
	

}
