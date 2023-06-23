package org.example.live.user.provider.service.impl;

import org.example.live.common.interfaces.ConvertBeanUtils;
import org.example.live.user.dto.UserDTO;
import org.example.live.user.provider.dao.mapper.IUserMapper;
import org.example.live.user.provider.dao.po.UserPO;
import org.example.live.user.provider.service.IUserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

@Service
public class UserServiceImpl implements IUserService {

	@Resource
	private IUserMapper userMapper;
	
	@Resource
	private RedisTemplate<String, UserDTO> redisTemplate;

	@Override
	public UserDTO getByUserId(Long userId) {
		if (userId == null) {
			return null;
		}
		// redis
		String keyString = "userInfo:" + userId;
		UserDTO userDTO = redisTemplate.opsForValue().get(keyString);
		if (userDTO != null) return userDTO;
		// mysql
		userDTO = ConvertBeanUtils.convert(
				userMapper.selectById(userId), UserDTO.class);
		if (userDTO != null) {
			redisTemplate.opsForValue().set(keyString, userDTO);
		}
		
		return userDTO;
	}

	@Override
	public boolean updateUserInfo(UserDTO userDTO) {
		if (userDTO == null || userDTO.getUserId() == null) {
			return false;
		}
		
		userMapper.updateById(
				ConvertBeanUtils.convert(userDTO, UserPO.class)
				);
		
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
}
