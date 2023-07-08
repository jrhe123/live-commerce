package org.example.live.user.provider.service.impl;

import java.util.List;

import org.example.live.user.dto.UserLoginDTO;
import org.example.live.user.dto.UserPhoneDTO;
import org.example.live.user.provider.dao.mapper.IUserPhoneMapper;
import org.example.live.user.provider.service.IUserPhoneService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

@Service
public class UserPhoneServiceImpl implements IUserPhoneService {
	
	@Resource
	private IUserPhoneMapper userPhoneMapper;
	

	@Override
	public UserLoginDTO login(String phone) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserPhoneDTO queryByPhone(String phone) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserPhoneDTO> queryByUserId(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
