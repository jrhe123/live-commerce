package org.example.live.api.service.impl;

import java.util.regex.Pattern;

import org.apache.dubbo.config.annotation.DubboReference;
import org.example.live.account.interfaces.IAccountTokenRPC;
import org.example.live.api.service.IUserLoginService;
import org.example.live.api.vo.UserLoginVO;
import org.example.live.common.interfaces.utils.ConvertBeanUtils;
import org.example.live.common.interfaces.vo.WebResponseVO;
import org.example.live.msg.dto.MsgCheckDTO;
import org.example.live.msg.enums.MsgSendResultEnum;
import org.example.live.msg.interfaces.ISmsRpc;
import org.example.live.user.dto.UserLoginDTO;
import org.example.live.user.interfaces.IUserPhoneRpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.nacos.api.utils.StringUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserLoginServiceImpl implements IUserLoginService {
	
	private static String PHONE_REG = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
	private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginServiceImpl.class);

	@DubboReference
	private ISmsRpc smsRpc;
	
	@DubboReference
	private IUserPhoneRpc userPhoneRpc;
	
	@DubboReference
	private IAccountTokenRPC accountTokenRPC;
	

	@Override
	public WebResponseVO sendLoginCode(String phone) {
		if (StringUtils.isEmpty(phone)) {
			return WebResponseVO.errorParam("phone is required");
		} else if (!Pattern.matches(PHONE_REG, phone)) {
			return WebResponseVO.errorParam("phone is invalid format");
		}
		
		MsgSendResultEnum sendLoginCode = smsRpc.sendLoginCode(phone);
		if (sendLoginCode == MsgSendResultEnum.SEND_SUCCESS) {
			return WebResponseVO.success();
		}
		
		System.out.println("sendLoginCode: " + sendLoginCode);
		
		return WebResponseVO.sysError("RPC error");
	}
	

	@Override
	public WebResponseVO login(
			String phone, Integer code, HttpServletResponse response) {
		
		if (StringUtils.isEmpty(phone)) {
			return WebResponseVO.errorParam("phone is required");
		} else if (!Pattern.matches(PHONE_REG, phone)) {
			return WebResponseVO.errorParam("phone is invalid format");
		} else if (code == null || code < 100000) {
			return WebResponseVO.errorParam("code is invalid format");
		}
		
		// 1. check code
		MsgCheckDTO checkLoginCode = smsRpc.checkLoginCode(phone, code);
		if (!checkLoginCode.isCheckStatus()) {
			return WebResponseVO.bizError(checkLoginCode.getDesc());
		}
		
		// 2. login & register
		UserLoginDTO login = userPhoneRpc.login(phone);
		if (!login.isLoginSuccess()) {
			LOGGER.error("login error, phone {}", phone);
			return WebResponseVO.sysError();
		}
		
		// 3. generate token from account provider
		String token = accountTokenRPC.createAndSaveLoginToken(login.getUserId());
		Cookie cookie = new Cookie("tk", token);
		cookie.setDomain("localhost");
		cookie.setPath("/");
		cookie.setMaxAge(30 * 24 * 3600);
		// web browser store cookie automatic
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.addCookie(cookie);
		
		return WebResponseVO.success(
				ConvertBeanUtils.convert(login, UserLoginVO.class)
				);
	}

}
