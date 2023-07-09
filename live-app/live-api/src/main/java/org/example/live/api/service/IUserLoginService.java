package org.example.live.api.service;

import org.example.live.common.interfaces.vo.WebResponseVO;

import jakarta.servlet.http.HttpServletResponse;

public interface IUserLoginService {

	WebResponseVO sendLoginCode(
			String phone);
	
	
	
	WebResponseVO login(
			String phone, Integer code, HttpServletResponse response);
}
