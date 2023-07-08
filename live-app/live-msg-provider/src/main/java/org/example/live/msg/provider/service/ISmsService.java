package org.example.live.msg.provider.service;

import org.example.live.msg.dto.MsgCheckDTO;
import org.example.live.msg.enums.MsgSendResultEnum;

public interface ISmsService {

	MsgSendResultEnum sendLoginCode(String phone);
	
	MsgCheckDTO checkLoginCode(String phone, Integer code);
	
	void insertOne(String phone, Integer code);
}
