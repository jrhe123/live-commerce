package org.example.live.user.interfaces;

import java.util.List;
import java.util.Map;

import org.example.live.user.dto.UserDTO;

public interface IUserRpc {

	String test();
	
	UserDTO getByUserId(Long userId);
	
	boolean updateUserInfo(UserDTO userDTO);
	
	boolean insertOne(UserDTO userDTO);
	
	Map<Long, UserDTO> batchQueryUserInfo(List<Long> userIdList);
}
