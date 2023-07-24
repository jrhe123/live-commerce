package org.example.live.im.core.server.handler.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.example.live.im.constants.ImMsgCodeEnum;
import org.example.live.im.core.server.common.ImMsg;
import org.example.live.im.core.server.handler.ImHandlerFactory;
import org.example.live.im.core.server.handler.SimplyHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.Resource;

@Component
public class ImHandlerFactoryImpl implements ImHandlerFactory, InitializingBean {
	
	private static Map<Integer, SimplyHandler> map = new HashMap<>();
	
//	static {
//		map.put(ImMsgCodeEnum.IM_LOGIN_MSG.getCode(), new LoginMsgHandler());
//		map.put(ImMsgCodeEnum.IM_LOGOUT_MSG.getCode(), new LogoutMsgHandler());
//		map.put(ImMsgCodeEnum.IM_BIZ_MSG.getCode(), new BizImMsgHandler());
//		map.put(ImMsgCodeEnum.IM_HEARTBEAT_MSG.getCode(), new HeartBeatImMsgHandler());
//	}
	
	@Resource
	private ApplicationContext applicationContext;

	@Override
	public void doMsgHandler(ChannelHandlerContext ctx, ImMsg imMsg) {
		
		SimplyHandler simplyHandler = map.get(imMsg.getCode());
		if (simplyHandler == null) {
			throw new IllegalArgumentException("Error: msg code: " + imMsg.getCode());
		}
		
		
		System.out.println("[ImHandlerFactoryImpl]: " + simplyHandler);
		
		
		simplyHandler.handler(ctx, imMsg);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
		map.put(ImMsgCodeEnum.IM_LOGIN_MSG.getCode(), 
				applicationContext.getBean(LoginMsgHandler.class));
		map.put(ImMsgCodeEnum.IM_LOGOUT_MSG.getCode(), 
				applicationContext.getBean(LogoutMsgHandler.class));
		map.put(ImMsgCodeEnum.IM_BIZ_MSG.getCode(), 
				applicationContext.getBean(BizImMsgHandler.class));
		map.put(ImMsgCodeEnum.IM_HEARTBEAT_MSG.getCode(), 
				applicationContext.getBean(HeartBeatImMsgHandler.class));
		
	}

}
