package org.example.live.accout.provider.service;

public interface IAccountTokenService {

	/**
	 * 
	 * create login token
	 * 
	 * @param userId
	 * @return
	 */
	String createAndSaveLoginToken(Long userId);
	
	/**
	 * 
	 * get userId by token
	 * 
	 * @param tokenKey
	 * @return
	 */
	Long getUserIdByToken(String tokenKey);
}
