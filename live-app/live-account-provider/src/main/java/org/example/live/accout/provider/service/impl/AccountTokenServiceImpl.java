package org.example.live.accout.provider.service.impl;

import org.example.live.accout.provider.service.IAccountTokenService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

@Service
public class AccountTokenServiceImpl implements IAccountTokenService {
	
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Resource
	

	@Override
	public String createAndSaveLoginToken(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getUserIdByToken(String tokenKey) {
		// TODO Auto-generated method stub
		return null;
	}

}
