package org.example.live.user.provider.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.dubbo.config.annotation.DubboReference;
import org.example.live.common.interfaces.enums.CommonStatusEnum;
import org.example.live.common.interfaces.utils.ConvertBeanUtils;
import org.example.live.common.interfaces.utils.DESUtils;
import org.example.live.framework.redis.starter.key.UserProviderCacheKeyBuilder;
import org.example.live.id.generate.enums.IdTypeEnum;
import org.example.live.id.generate.interfaces.IdGenerateRpc;
import org.example.live.user.dto.UserDTO;
import org.example.live.user.dto.UserLoginDTO;
import org.example.live.user.dto.UserPhoneDTO;
import org.example.live.user.provider.dao.mapper.IUserPhoneMapper;
import org.example.live.user.provider.dao.po.UserPhonePO;
import org.example.live.user.provider.service.IUserPhoneService;
import org.example.live.user.provider.service.IUserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.nacos.api.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import jakarta.annotation.Resource;

@Service
public class UserPhoneServiceImpl implements IUserPhoneService {
	
	@Resource
	private IUserPhoneMapper userPhoneMapper;
	
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Resource
	private UserProviderCacheKeyBuilder userProviderCacheKeyBuilder;
	
	@Resource
	private IUserService userService;
	
	@DubboReference
	private IdGenerateRpc idGenerateRpc;
	

	@Override
	public UserLoginDTO login(String phone) {
		// 1. phone is required
		if (StringUtils.isEmpty(phone)) {
			return null;
		}
		
		// 2. check if it's registered
		UserPhoneDTO queryByPhone = queryByPhone(phone);
		
		// 3. if it exists, create token & return userId
		if (queryByPhone != null) {
			String token = createAndSaveLoginToken(queryByPhone.getUserId());
			return UserLoginDTO.loginSuccess(
					queryByPhone.getUserId(),token);
		}
		
		// 4. if not, create user & user phone
		UserLoginDTO registerAndLogin = registerAndLogin(phone);
		
		return registerAndLogin;
	}
	
	public String createAndSaveLoginToken(Long userId) {
		String token = UUID.randomUUID().toString();
		String redisKey = userProviderCacheKeyBuilder.buildUserLoginTokenKey(token);
		
		/**
		 * KEY : VALUE
		 * xxxx-xxx-token -> userId
		 */
		
		redisTemplate.opsForValue().set(redisKey, userId, 30, TimeUnit.DAYS);
		
		return token;
	}
	
	private UserLoginDTO registerAndLogin(String phone) {
		// 1. create user
		UserDTO userDTO = new UserDTO();
		// use strategy "USER_ID"
		Long userId = idGenerateRpc.getSeqId(
				IdTypeEnum.USER_ID.getCode());
		userDTO.setUserId(userId);
		userDTO.setNickName("stream-user-" + userId);
		userService.insertOne(userDTO);
		
		// 2. create user phone
		UserPhonePO userPhonePO = new UserPhonePO();
		userPhonePO.setUserId(userId);
		userPhonePO.setPhone(phone);
		userPhonePO.setStatus(CommonStatusEnum.VALID_STATUS.getCode());
		userPhoneMapper.insert(userPhonePO);
		
		return UserLoginDTO.loginSuccess(
				userId, createAndSaveLoginToken(userId));
	}

	@Override
	public UserPhoneDTO queryByPhone(String phone) {
		if (StringUtils.isEmpty(phone)) {
			return null;
		}
		
		// check redis cache
		String redisKey = userProviderCacheKeyBuilder.buildUserPhoneObjKey(phone);
		UserPhoneDTO userPhoneDTO = (UserPhoneDTO) redisTemplate.opsForValue().get(redisKey);
		if (userPhoneDTO != null) {
			// "cache breakdown"
			// check if it's null cache we set
			if (userPhoneDTO.getUserId() == null) {
				return null;
			}
			return userPhoneDTO;
		}
		
		// query from DB
		userPhoneDTO = queryByPhoneFromDB(phone);
		if (userPhoneDTO != null) {
			redisTemplate.opsForValue().set(redisKey, userPhoneDTO, 30, TimeUnit.MINUTES);
			return userPhoneDTO;
		}
		
		/**
		 * "cache breakdown"
		 * -> cache a null UserPhoneDTO
		 */
		userPhoneDTO = new UserPhoneDTO();
		redisTemplate.opsForValue().set(redisKey, userPhoneDTO, 5, TimeUnit.MINUTES);
		
		return null;
	}
	
	private UserPhoneDTO queryByPhoneFromDB(String phone) {
		LambdaQueryWrapper<UserPhonePO> queryWrapper = new LambdaQueryWrapper<>();
		
		String decryptedPhone = DESUtils.decrypt(phone);
		queryWrapper.eq(UserPhonePO::getPhone, decryptedPhone);
		
		queryWrapper.eq(UserPhonePO::getStatus, CommonStatusEnum.VALID_STATUS.getCode());
		queryWrapper.last("limit 1");
		UserPhonePO selectOne = userPhoneMapper.selectOne(queryWrapper);
		
		return ConvertBeanUtils.convert(selectOne, UserPhoneDTO.class);
	}

	@Override
	public List<UserPhoneDTO> queryByUserId(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
