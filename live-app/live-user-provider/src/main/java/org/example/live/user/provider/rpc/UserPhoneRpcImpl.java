package org.example.live.user.provider.rpc;

import java.util.List;

import org.apache.dubbo.config.annotation.DubboService;
import org.example.live.user.dto.UserLoginDTO;
import org.example.live.user.dto.UserPhoneDTO;
import org.example.live.user.interfaces.IUserPhoneRpc;

@DubboService
public class UserPhoneRpcImpl implements IUserPhoneRpc {

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
