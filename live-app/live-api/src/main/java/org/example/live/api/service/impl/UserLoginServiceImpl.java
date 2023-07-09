package org.example.live.api.service.impl;

import java.util.regex.Pattern;

import org.apache.dubbo.config.annotation.DubboReference;
import org.example.live.api.service.IUserLoginService;
import org.example.live.common.interfaces.vo.WebResponseVO;
import org.example.live.msg.enums.MsgSendResultEnum;
import org.example.live.msg.interfaces.ISmsRpc;
import org.example.live.user.interfaces.IUserPhoneRpc;
import org.springframework.stereotype.Service;

import com.alibaba.nacos.api.utils.StringUtils;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserLoginServiceImpl implements IUserLoginService {
	
	private static String PHONE_REG = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";

	@DubboReference
	private ISmsRpc smsRpc;
	
	@DubboReference
	private IUserPhoneRpc userPhoneRpc;
	

	@Override
	public WebResponseVO sendLoginCode(String phone) {
		if (StringUtils.isEmpty(phone)) {
			return WebResponseVO.errorParam("phone is required");
		} else if (!Pattern.matches(PHONE_REG, phone)) {
			return WebResponseVO.errorParam("phone is invalid format");
		}
		
		MsgSendResultEnum sendLoginCode = smsRpc.sendLoginCode(phone);
		
		
		return null;
	}
	

	@Override
	public WebResponseVO login(String phone, Integer code, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

}
