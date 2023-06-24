package org.example.live.user.provider.rpc;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.DubboService;
import org.example.live.user.dto.UserDTO;
import org.example.live.user.interfaces.IUserRpc;
import org.example.live.user.provider.service.IUserService;

import jakarta.annotation.Resource;


//@DubboService(
//		group = ""
//		)
@DubboService
public class UserRpcImpl implements IUserRpc {
	
	@Resource
	private IUserService userService;

	@Override
	public String test() {
		System.out.println("test dubbo service");
		return "OK";
	}

	@Override
	public UserDTO getByUserId(Long userId) {
		// TODO Auto-generated method stub
		return userService.getByUserId(userId);
	}

	@Override
	public boolean updateUserInfo(UserDTO userDTO) {
		// TODO Auto-generated method stub
		return userService.updateUserInfo(userDTO);
	}

	@Override
	public boolean insertOne(UserDTO userDTO) {
		// TODO Auto-generated method stub
		return userService.insertOne(userDTO);
	}

	@Override
	public Map<Long, UserDTO> batchQueryUserInfo(List<Long> userIdList) {
		// TODO Auto-generated method stub
		return userService.batchQueryUserInfo(userIdList);
	}

}
