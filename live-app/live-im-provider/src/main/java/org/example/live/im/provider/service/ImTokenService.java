package org.example.live.im.provider.service;

public interface ImTokenService {

	
	/**
	 * 
	 * create user login im service token
	 * 
	 * @param userId
	 * @param appId
	 * @return
	 */
	String createImLoginToken(long userId, int appId);
	
	/**
	 * 
	 * get userId from im token
	 * 
	 * @param token
	 * @return
	 */
	Long getUserIdByToken(String token);
}
