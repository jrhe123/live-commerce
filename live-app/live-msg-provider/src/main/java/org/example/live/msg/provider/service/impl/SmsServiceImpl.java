package org.example.live.msg.provider.service.impl;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomUtils;
import org.example.live.framework.redis.starter.key.MsgProviderCacheKeyBuilder;
import org.example.live.msg.dto.MsgCheckDTO;
import org.example.live.msg.enums.MsgSendResultEnum;
import org.example.live.msg.provider.config.ApplicationProperties;
import org.example.live.msg.provider.dao.mapper.SmsMapper;
import org.example.live.msg.provider.service.ISmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.nacos.api.utils.StringUtils;

import jakarta.annotation.Resource;

public class SmsServiceImpl implements ISmsService {
	
	private static Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);

	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Resource
	private MsgProviderCacheKeyBuilder msgProviderCacheKeyBuilder;
	
	@Resource
	private SmsMapper smsMapper;
	
	@Resource
	private ApplicationProperties applicationProperties;
	
	
	@Override
	public MsgSendResultEnum sendLoginCode(String phone) {
		if(StringUtils.isEmpty(phone)) {
			return MsgSendResultEnum.MSG_PARAM_ERROR;
		}
		/**
		 * 
		 * rule:
		 * 1. 6 digits
		 * 2. expire time: 60 seconds
		 * 3. same phone number cannot send repeat
		 * 4. Redis store code
		 * 
		 */
		// 1. create code
		String codeCacheKeyString = msgProviderCacheKeyBuilder.buildSmsLoginCodeKey(phone);
		if (redisTemplate.hasKey(codeCacheKeyString)) {
			logger.warn("this is phone number: {} sending sms multiple times", phone);
			return MsgSendResultEnum.SEND_FAIL;
		}
		int code = RandomUtils.nextInt(100000, 999999);
		// 2. save it into Redis
		redisTemplate.opsForValue().set(codeCacheKeyString, code,
				60, TimeUnit.SECONDS);
		// 3. send code
		
		return null;
	}

	@Override
	public MsgCheckDTO checkLoginCode(String phone, Integer code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertOne(String phone, Integer code) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * mock sms send
	 * 
	 * @param phone
	 * @param code
	 * @return
	 */
	@SuppressWarnings("finally")
	private boolean mockSendSms(String phone, Integer code) {
		try {
			logger.info("============== mock create sms ==============");
			logger.info("phone: {}, sms code: {}", phone, code);
			Thread.sleep(1000);
			logger.info("============== mock sms sent ==============");
			return true;
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			return false;
		}
	}
	
}
