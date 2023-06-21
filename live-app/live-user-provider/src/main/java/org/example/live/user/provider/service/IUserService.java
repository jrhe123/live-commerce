package org.example.live.user.provider.service;

import org.example.live.user.dto.UserDTO;

public interface IUserService {

	
	UserDTO getByUserId(Long userId);
	
	boolean updateUserInfo(UserDTO userDTO);
	
	boolean insertOne(UserDTO userDTO);
}
