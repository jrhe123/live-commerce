package org.example.live.im.provider.service.impl;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.example.live.framework.redis.starter.key.ImProviderCacheKeyBuilder;
import org.example.live.im.provider.service.ImTokenService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

@Service
public class ImTokenServiceImpl implements ImTokenService {
	
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Resource
	private ImProviderCacheKeyBuilder cacheKeyBuilder;
	

	@Override
	public String cressteImLoginToken(long userId, int appId) {
		String token = UUID.randomUUID() + "%" + appId;
		String redisKey = cacheKeyBuilder.buildImLoginTokenKey(token);
		
		redisTemplate.opsForValue().set(redisKey, userId,
				5, TimeUnit.MINUTES);
		
		return token;
	}

	@Override
	public Long getUserIdByToken(String token) {
		String redisKey = cacheKeyBuilder.buildImLoginTokenKey(token);
		Object userId = redisTemplate.opsForValue().get(redisKey);
		
		return userId == null ? null : Long.valueOf((Integer) userId);
	}

}
