package org.example.live.user.provider.service.impl;

import java.util.List;

import org.example.live.common.interfaces.enums.CommonStatusEnum;
import org.example.live.user.dto.UserLoginDTO;
import org.example.live.user.dto.UserPhoneDTO;
import org.example.live.user.provider.dao.mapper.IUserPhoneMapper;
import org.example.live.user.provider.dao.po.UserPhonePO;
import org.example.live.user.provider.service.IUserPhoneService;
import org.springframework.stereotype.Service;

import com.alibaba.nacos.api.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import jakarta.annotation.Resource;

@Service
public class UserPhoneServiceImpl implements IUserPhoneService {
	
	@Resource
	private IUserPhoneMapper userPhoneMapper;
	

	@Override
	public UserLoginDTO login(String phone) {
		// 1. phone is required
		if (StringUtils.isEmpty(phone)) {
			return null;
		}
		
		// 2. check if it's registered
		LambdaQueryWrapper<UserPhonePO> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(UserPhonePO::getPhone, phone);
		queryWrapper.eq(UserPhonePO::getStatus, CommonStatusEnum.VALID_STATUS.getCode());
		queryWrapper.last("limit 1");
		userPhoneMapper.selectOne(queryWrapper);
		
		// 3. if it exists, create token & return userId
		
		// 4. if not, create user, user phone
		
		
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
