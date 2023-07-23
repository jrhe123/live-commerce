package org.example.live.im.core.server.handler.impl;

import org.apache.dubbo.config.annotation.DubboReference;
import org.example.live.im.constants.AppIdEnum;
import org.example.live.im.constants.ImMsgCodeEnum;
import org.example.live.im.core.server.common.ImMsg;
import org.example.live.im.core.server.handler.SimplyHandler;
import org.example.live.im.dto.ImMsgBody;
import org.example.live.im.interfaces.ImTokenRpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.shaded.io.grpc.netty.shaded.io.netty.util.internal.StringUtil;

import io.netty.channel.ChannelHandlerContext;

public class LoginMsgHandler implements SimplyHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginMsgHandler.class);
	
	
	@DubboReference
	private ImTokenRpc imTokenRpc;

	@Override
	public void handler(ChannelHandlerContext ctx, ImMsg imMsg) {
		System.out.println("this is login message handler");
		
		byte[] body = imMsg.getBody();
		
		if (body == null || body.length == 0) {
			ctx.close();
			LOGGER.error("Error: body error, imMsg: {}", imMsg);
			throw new IllegalArgumentException("Error: body error");
		}
		
		ImMsgBody imMsgBody = JSON.parseObject(new String(body), ImMsgBody.class);
		String token = imMsgBody.getToken();
		if (StringUtils.isEmpty(token)) {
			ctx.close();
			LOGGER.error("Error: token error, imMsg: {}", imMsg);
			throw new IllegalArgumentException("Error: token error");
		}
		
		Long userId = imTokenRpc.getUserIdByToken(token);
		if (userId != null && userId.equals(imMsgBody.getUserId())) {
			// valid success
			
			ImMsgBody resBody = new ImMsgBody();
			resBody.setAppId(AppIdEnum.LIVE_BIZ_APP.getCode());
			resBody.setUserId(userId);
			resBody.setData("true");
			
			ImMsg resMsg = ImMsg.build(
					ImMsgCodeEnum.IM_LOGIN_MSG.getCode(), JSON.toJSONString(resBody));
			
			ctx.writeAndFlush(resMsg);
			return;
		}
		
		ctx.close();
		LOGGER.error("Error: token check error, imMsg: {}", imMsg);
		throw new IllegalArgumentException("Error: token check error");
	}

}
