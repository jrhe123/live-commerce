package org.example.live.msg.interfaces;

import org.example.live.msg.dto.MsgCheckDTO;
import org.example.live.msg.enums.MsgSendResultEnum;

public interface ISmsRpc {

	/**
	 * send verify code
	 * 
	 * @param phone
	 * @return
	 */
	MsgSendResultEnum sendLoginCode(String phone);
	
	
	/**
	 * check verify code
	 * 
	 * @param phone
	 * @param code
	 * @return
	 */
	MsgCheckDTO checkLoginCode(String phone, Integer code);
}
