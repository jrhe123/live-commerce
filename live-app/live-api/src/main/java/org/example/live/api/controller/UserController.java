package org.example.live.api.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.example.live.user.dto.UserDTO;
import org.example.live.user.interfaces.IUserRpc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@DubboReference
	private IUserRpc userRpc;

	@GetMapping("/getUserInfo")
	public UserDTO getUserInfo(Long userId) {
		return userRpc.getByUserId(userId);
	}
	
	@GetMapping("/updateUserInfo")
	public boolean updateUserInfo(Long userId, String nickname) {
		UserDTO userDTO = new UserDTO();
		userDTO.setUserId(userId);
		userDTO.setNickName(nickname);
		
		return userRpc.updateUserInfo(userDTO);
	}
	
	@GetMapping("/insertOne")
	public boolean insertOne(Long userId) {
		UserDTO userDTO = new UserDTO();
		userDTO.setUserId(userId);
		userDTO.setNickName("this is api nickname");
		userDTO.setSex(1);
		
		return userRpc.insertOne(userDTO);
	}
}
