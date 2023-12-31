package org.example.live.user.interfaces;

import java.util.List;

import org.example.live.user.dto.UserLoginDTO;
import org.example.live.user.dto.UserPhoneDTO;

public interface IUserPhoneRpc {

	/**
	 * user login & register
	 * 
	 * @param phone
	 * @return
	 */
	UserLoginDTO login(String phone);
	
	
	/**
	 * search user phone by phone number
	 * 
	 * @param phone
	 * @return
	 */
	UserPhoneDTO queryByPhone(String phone);
	
	
	/**
	 * get all user-phone(s) by use id
	 * 
	 * @param userId
	 * @return
	 */
	List<UserPhoneDTO> queryByUserId(Long userId);
}
