package org.example.live.api.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	
	@GetMapping("/batchQueryUserInfo")
	public Map<Long, UserDTO> batchQueryUserInfo(String userIdStr) {
		
		List<Long> userIdList = Arrays.asList(userIdStr.split(","))
			.stream().map(x->Long.valueOf(x))
			.collect(Collectors.toList());
		return userRpc.batchQueryUserInfo(userIdList);
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
