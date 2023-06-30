package org.example.live.api.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.dubbo.config.annotation.DubboReference;
import org.example.live.user.contants.UserTagsEnum;
import org.example.live.user.dto.UserDTO;
import org.example.live.user.interfaces.IUserRpc;
import org.example.live.user.interfaces.IUserTagRpc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@DubboReference
	private IUserRpc userRpc;
	
	@DubboReference
	private IUserTagRpc userTagRpc;

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
	
	@GetMapping("/testUserInfoAndTag")
	public HashMap testUserInfoAndTag() {
		Long userIdLong = 11114L;
//		UserDTO userDTO = userRpc.getByUserId(userIdLong);
//		userDTO.setNickName("test updated");
//		userRpc.updateUserInfo(userDTO);
		
		HashMap<String, Boolean> resultMap = new HashMap<>();
				
		boolean containTag = userTagRpc.containTag(userIdLong, UserTagsEnum.IS_OLD_USER);
		resultMap.put("1-initial", containTag);
		
		boolean setTag = userTagRpc.setTag(userIdLong, UserTagsEnum.IS_OLD_USER);
		resultMap.put("2-set", setTag);
		
		containTag = userTagRpc.containTag(userIdLong, UserTagsEnum.IS_OLD_USER);
		resultMap.put("3-check", containTag);
		
		boolean cancelTag = userTagRpc.cancelTag(userIdLong, UserTagsEnum.IS_OLD_USER);
		resultMap.put("4-cancel", cancelTag);
		
		containTag = userTagRpc.containTag(userIdLong, UserTagsEnum.IS_OLD_USER);
		resultMap.put("5-check", containTag);
		
		return resultMap;
	}
}
