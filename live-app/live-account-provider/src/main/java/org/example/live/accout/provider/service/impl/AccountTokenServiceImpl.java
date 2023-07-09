package org.example.live.accout.provider.service.impl;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.example.live.accout.provider.service.IAccountTokenService;
import org.example.live.framework.redis.starter.key.AccountProviderCacheKeyBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

@Service
public class AccountTokenServiceImpl implements IAccountTokenService {
	
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Resource
	private AccountProviderCacheKeyBuilder cacheKeyBuilder;

	@Override
	public String createAndSaveLoginToken(Long userId) {
		String tokenString = UUID.randomUUID().toString();
		String redisKey = cacheKeyBuilder.buildUserLoginTokenKey(tokenString);
		redisTemplate.opsForValue().set(redisKey, String.valueOf(userId), 
				30, TimeUnit.DAYS);
		return tokenString;
	}

	@Override
	public Long getUserIdByToken(String tokenKey) {
		String redisKey = cacheKeyBuilder.buildUserLoginTokenKey(tokenKey);
		Integer userId = (Integer) redisTemplate.opsForValue().get(redisKey);
		
		return userId == null ? null : Long.valueOf(userId);
	}

}
