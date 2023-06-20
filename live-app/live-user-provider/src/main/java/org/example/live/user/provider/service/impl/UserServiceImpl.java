package org.example.live.user.provider.service.impl;

import org.example.live.user.dto.UserDTO;
import org.example.live.user.provider.dao.mapper.IUserMapper;
import org.example.live.user.provider.service.IUserService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

@Service
public class UserServiceImpl implements IUserService {

	@Resource
	private IUserMapper userMapper;

	@Override
	public UserDTO getByUserId(Long userId) {
		if (userId == null) {
			return null;
		}
		return userMapper.selectById(userId);
	}
}
