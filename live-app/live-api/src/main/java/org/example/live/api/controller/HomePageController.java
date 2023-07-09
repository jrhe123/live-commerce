package org.example.live.api.controller;

import org.example.live.common.interfaces.enums.GatewayHeaderEnum;
import org.example.live.common.interfaces.vo.WebResponseVO;
import org.example.live.framework.web.starter.StreamRequestContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/home")
public class HomePageController {

	/**
	 * 
	 * heart-beat call
	 * 1. logged in, token valid
	 * 2. check userId
	 * @return
	 */
	@PostMapping("/initPage")
	public WebResponseVO initPage(
			// HttpServletRequest request
			) {
		/**
		 * 
		 * v1
		 * Long userId = Long.valueOf(request.getHeader(GatewayHeaderEnum.USER_LOGIN_ID.getName()));
		 * 
		 */
		
		/**
		 * 
		 * v2
		 * get userId from context
		 * 
		 */
		Long userId = StreamRequestContext.getUserId();
		
		System.out.println("!!!!!!!!!");
		System.out.println("!!!!!!!!!");
		System.out.println("!!!!!!!!!");
		System.out.println("!!!!!!!!!");
		System.out.println("!!!!!!!!!");
		System.out.println("userId: " + userId);
		
		return WebResponseVO.success();
	}
	
}
