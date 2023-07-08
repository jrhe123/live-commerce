package org.example.live.user.provider.rpc;

import java.util.List;

import org.apache.dubbo.config.annotation.DubboService;
import org.example.live.user.dto.UserLoginDTO;
import org.example.live.user.dto.UserPhoneDTO;
import org.example.live.user.interfaces.IUserPhoneRpc;
import org.example.live.user.provider.service.IUserPhoneService;

import jakarta.annotation.Resource;

@DubboService
public class UserPhoneRpcImpl implements IUserPhoneRpc {

	@Resource
	private IUserPhoneService userPhoneService;
	
	@Override
	public UserLoginDTO login(String phone) {
		return userPhoneService.login(phone);
	}

	@Override
	public UserPhoneDTO queryByPhone(String phone) {
		return userPhoneService.queryByPhone(phone);
	}

	@Override
	public List<UserPhoneDTO> queryByUserId(Long userId) {
		return userPhoneService.queryByUserId(userId);
	}

}
