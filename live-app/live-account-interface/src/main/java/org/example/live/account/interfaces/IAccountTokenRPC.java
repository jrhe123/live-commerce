package org.example.live.account.interfaces;

public interface IAccountTokenRPC {

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
