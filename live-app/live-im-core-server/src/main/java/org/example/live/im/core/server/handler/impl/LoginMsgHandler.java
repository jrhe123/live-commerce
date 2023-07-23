package org.example.live.im.core.server.handler.impl;

import org.apache.dubbo.config.annotation.DubboReference;
import org.example.live.im.constants.AppIdEnum;
import org.example.live.im.constants.ImMsgCodeEnum;
import org.example.live.im.core.server.common.ChannelHandlerContextCache;
import org.example.live.im.core.server.common.ImContextUtils;
import org.example.live.im.core.server.common.ImMsg;
import org.example.live.im.core.server.handler.SimplyHandler;
import org.example.live.im.dto.ImMsgBody;
import org.example.live.im.interfaces.ImTokenRpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;

import io.netty.channel.ChannelHandlerContext;

@Component
public class LoginMsgHandler implements SimplyHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginMsgHandler.class);
	
	
	@DubboReference
	private ImTokenRpc imTokenRpc;

	@Override
	public void handler(ChannelHandlerContext ctx, ImMsg imMsg) {
		//防止重复请求
        if (ImContextUtils.getUserId(ctx) != null) {
            return;
        }
		
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
		
		Integer appId = imMsgBody.getAppId();
		Long userId = imTokenRpc.getUserIdByToken(token);
		if (userId != null && userId.equals(imMsgBody.getUserId())) {
			// valid success
			
			// 1. save it into map, map userId & context
			ChannelHandlerContextCache.put(userId, ctx);
			
			// 2. add attribute to context, for "正常/意外断线"
			ImContextUtils.setUserId(ctx, userId);
            ImContextUtils.setAppId(ctx, appId);
			
			// 3. build response body & msg
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
