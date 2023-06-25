package org.example.live.user.provider.service.impl;

import java.nio.charset.StandardCharsets;

import org.example.live.framework.redis.starter.key.UserProviderCacheKeyBuilder;
import org.example.live.user.contants.UserTagFieldNameConstants;
import org.example.live.user.contants.UserTagsEnum;
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

import jakarta.annotation.Resource;

@Service
public class UserTagServiceImpl implements IUserTagService {
	
	@Resource
	private IUserTagMapper userTagMapper;
	
	@Resource
	private RedisTemplate<String, String> redisTemplate;
	
	@Resource
	private UserProviderCacheKeyBuilder userProviderCacheKeyBuilder;
	

	@Override
	public boolean setTag(Long userId, UserTagsEnum userTagsEnum) {
		boolean updateStatus = userTagMapper.setTag(
				userId, userTagsEnum.getFieldName(), userTagsEnum.getTag()
				)  > 0;
				
		
		if (updateStatus) {
			return true;
		}
		
		UserTagPO userTagPO = userTagMapper.selectById(userId);
		
		// already exists
		if (userTagPO != null) {
			return false;
		}
		
		// We need to prevent multiple threads trying to insert the record
		// So we use redis to check
		String redisKey = userProviderCacheKeyBuilder.buildTagLockKey(userId);
		String setNxResult = redisTemplate.execute(new RedisCallback<String>() {

			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer keySerializer = redisTemplate.getKeySerializer();
				RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
				
				String result = (String) connection.execute(
						"set",
						keySerializer.serialize(redisKey),
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
		
		// insert
		userTagPO = new UserTagPO();
		userTagPO.setUserId(userId);
		userTagMapper.insert(userTagPO);
		
		updateStatus = userTagMapper.setTag(
				userId, userTagsEnum.getFieldName(), userTagsEnum.getTag()
				)  > 0;
		
		// release the redis
		redisTemplate.delete(redisKey);
		
		return updateStatus;
	}

	@Override
	public boolean cancelTag(Long userId, UserTagsEnum userTagsEnum) {
		int cancelTag = userTagMapper.cancelTag(
				userId, userTagsEnum.getFieldName(), userTagsEnum.getTag()
				);
		return cancelTag > 0;
	}

	@Override
	public boolean containTag(Long userId, UserTagsEnum userTagsEnum) {
//		UserTagPO userTagPO = userTagMapper.selectByUserId(userId);
		UserTagPO userTagPO = userTagMapper.selectById(userId);
		
		System.out.println("userTagPO: " + userTagPO);
		
		if (userTagPO == null) {
			return false;
		}
		
		String fieldName = userTagsEnum.getFieldName();
		Long tagInfo;
		
		if (UserTagFieldNameConstants.TAG_INFO_01.equals(fieldName)) {
			tagInfo = userTagPO.getTagInfo01();
			return TagInfoUtils.isContain(tagInfo, userTagsEnum.getTag());
		} else if (UserTagFieldNameConstants.TAG_INFO_02.equals(fieldName)) {
			tagInfo = userTagPO.getTagInfo02();
			return TagInfoUtils.isContain(tagInfo, userTagsEnum.getTag());
		} else if (UserTagFieldNameConstants.TAG_INFO_03.equals(fieldName)) {
			tagInfo = userTagPO.getTagInfo03();
			return TagInfoUtils.isContain(tagInfo, userTagsEnum.getTag());
		}
		
		return false;
	}

	

}
