package org.example.live.im.core.server.handler.impl;

import org.example.live.im.constants.ImMsgCodeEnum;
import org.example.live.im.core.server.common.ChannelHandlerContextCache;
import org.example.live.im.core.server.common.ImContextUtils;
import org.example.live.im.core.server.common.ImMsg;
import org.example.live.im.core.server.handler.SimplyHandler;
import org.example.live.im.dto.ImMsgBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.Resource;

@Component
public class LogoutMsgHandler implements SimplyHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogoutMsgHandler.class);

	@Resource
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public void handler(ChannelHandlerContext ctx, ImMsg imMsg) {
		
		// get it from attribute context
		Long userId = ImContextUtils.getUserId(ctx);
		Integer appId = ImContextUtils.getAppId(ctx);
		
		
		if (userId == null || appId == null) {
			LOGGER.error("attr error,imMsg is {}", imMsg);
			ctx.close();
			throw new IllegalArgumentException("attr is error");
		}
		
		// im response
		ImMsgBody respBody = new ImMsgBody();
		respBody.setAppId(appId);
		respBody.setUserId(userId);
		respBody.setData("true");
		ImMsg respMsg = ImMsg.build(ImMsgCodeEnum.IM_LOGOUT_MSG.getCode(), JSON.toJSONString(respBody));
		ctx.writeAndFlush(respMsg);
		
		LOGGER.info("[LogoutMsgHandler] logout success,userId is {},appId is {}", userId, appId);
		// 理想情况下，客户端断线的时候，会发送一个断线消息包
		ChannelHandlerContextCache.remove(userId);
		
//		stringRedisTemplate.delete(ImCoreServerConstants.IM_BIND_IP_KEY + appId + userId);
		
		
		// remove it from map
		ImContextUtils.removeUserId(ctx);
		ImContextUtils.removeAppId(ctx);
		
		
		ctx.close();
	}

}
