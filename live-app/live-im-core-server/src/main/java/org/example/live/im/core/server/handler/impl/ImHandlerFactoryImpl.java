package org.example.live.im.core.server.handler.impl;

import java.util.HashMap;
import java.util.Map;

import org.example.live.im.constants.ImMsgCodeEnum;
import org.example.live.im.core.server.common.ImMsg;
import org.example.live.im.core.server.handler.ImHandlerFactory;
import org.example.live.im.core.server.handler.SimplyHandler;

import io.netty.channel.ChannelHandlerContext;

public class ImHandlerFactoryImpl implements ImHandlerFactory {
	
	private static Map<Integer, SimplyHandler> map = new HashMap<>();
	
	static {
		map.put(ImMsgCodeEnum.IM_LOGIN_MSG.getCode(), new LoginMsgHandler());
		map.put(ImMsgCodeEnum.IM_LOGOUT_MSG.getCode(), new LogoutMsgHandler());
		map.put(ImMsgCodeEnum.IM_BIZ_MSG.getCode(), new BizImMsgHandler());
		map.put(ImMsgCodeEnum.IM_HEARTBEAT_MSG.getCode(), new HeartBeatImMsgHandler());
	}

	@Override
	public void doMsgHandler(ChannelHandlerContext ctx, ImMsg imMsg) {
		
		SimplyHandler simplyHandler = map.get(imMsg.getCode());
		if (simplyHandler == null) {
			throw new IllegalArgumentException("Error: msg code: " + imMsg.getCode());
		}
		
		
		simplyHandler.handler(ctx, imMsg);
	}

}
