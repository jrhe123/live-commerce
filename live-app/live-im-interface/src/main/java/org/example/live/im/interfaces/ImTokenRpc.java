package org.example.live.im.interfaces;

public interface ImTokenRpc {

	
	/**
	 * 
	 * create user login im service token
	 * 
	 * @param userId
	 * @param appId
	 * @return
	 */
	String cressteImLoginToken(long userId, int appId);
	
	/**
	 * 
	 * get userId from im token
	 * 
	 * @param token
	 * @return
	 */
	Long getUserIdByToken(String token);
}
