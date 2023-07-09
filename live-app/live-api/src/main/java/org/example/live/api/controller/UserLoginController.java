package org.example.live.api.controller;

import org.example.live.api.service.IUserLoginService;
import org.example.live.common.interfaces.vo.WebResponseVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/userLogin")
public class UserLoginController {

	@Resource
	private IUserLoginService userLoginService;
	
	/**
	 * 
	 * user send verify code with SMS
	 * 
	 * @param phone
	 * @return
	 */
	@PostMapping("/sendLoginCode")
	public WebResponseVO sendLoginCode(String phone) {
		return userLoginService.sendLoginCode(phone);
	}
	
	
	/**
	 * 
	 * user login (register if needed)
	 * 
	 * @param phone
	 * @param code
	 * @param response
	 * @return
	 */
	@PostMapping("/login")
	public WebResponseVO login(
			String phone, Integer code, HttpServletResponse response) {
		return userLoginService.login(phone, code, response);
	}
}
