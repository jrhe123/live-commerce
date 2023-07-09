package org.example.live.msg.provider.rpc;

import org.apache.dubbo.config.annotation.DubboService;
import org.example.live.msg.dto.MsgCheckDTO;
import org.example.live.msg.enums.MsgSendResultEnum;
import org.example.live.msg.interfaces.ISmsRpc;
import org.example.live.msg.provider.service.ISmsService;

import jakarta.annotation.Resource;

@DubboService
public class SmsRpcImpl implements ISmsRpc {
	
	@Resource
	private ISmsService smsService;

	@Override
	public MsgSendResultEnum sendLoginCode(String phone) {
		return smsService.sendLoginCode(phone);
	}

	@Override
	public MsgCheckDTO checkLoginCode(String phone, Integer code) {
		return smsService.checkLoginCode(phone, code);
	}

}
