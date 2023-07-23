package org.example.live.im.provider.service.impl;

import org.example.live.im.provider.service.ImTokenService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

@Service
public class ImTokenServiceImpl implements ImTokenService {
	
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	

	@Override
	public String cressteImLoginToken(long userId, int appId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getUserIdByToken(String token) {
		// TODO Auto-generated method stub
		return null;
	}

}
