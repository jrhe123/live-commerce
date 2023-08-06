package org.example.live.im.core.server.handler.impl;

import java.util.concurrent.TimeUnit;

import org.apache.dubbo.config.annotation.DubboReference;
import org.example.live.im.constants.AppIdEnum;
import org.example.live.im.constants.ImConstants;
import org.example.live.im.constants.ImMsgCodeEnum;
import org.example.live.im.core.server.common.ChannelHandlerContextCache;
import org.example.live.im.core.server.common.ImContextUtils;
import org.example.live.im.core.server.common.ImMsg;
import org.example.live.im.core.server.handler.SimplyHandler;
import org.example.live.im.dto.ImMsgBody;
import org.example.live.im.interfaces.ImTokenRpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;
import com.example.live.im.core.server.interfaces.constants.ImCoreServerConstants;

import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.Resource;

@Component
public class LoginMsgHandler implements SimplyHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginMsgHandler.class);
	
	
	@DubboReference
	private ImTokenRpc imTokenRpc;
	
	@Resource
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public void handler(ChannelHandlerContext ctx, ImMsg imMsg) {
		// prevent duplicate request
        if (ImContextUtils.getUserId(ctx) != null) {
            return;
        }
        
		
		byte[] body = imMsg.getBody();
		if (body == null || body.length == 0) {
			ctx.close();
			LOGGER.error("Error: body error, imMsg: {}", imMsg);
			throw new IllegalArgumentException("Error: body error");
		}
		
		
		// convert byte[] -> String -> ImMsgBody
		ImMsgBody imMsgBody = JSON.parseObject(new String(body), ImMsgBody.class);
		String token = imMsgBody.getToken();
		
		
		System.out.println(">>>>>>>>> [LoginMsgHandler]: token: " + token);
		
		
		Integer appId = imMsgBody.getAppId();
		if (StringUtils.isEmpty(token) || appId < 10000) {
			ctx.close();
			LOGGER.error("Error: params error, imMsg: {}", imMsg);
			throw new IllegalArgumentException("Error: token error");
		}
		
		
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
			
			// 记录im server ip 在 redis
			stringRedisTemplate.opsForValue().set(
					ImCoreServerConstants.IM_BIND_IP_KEY + appId + userId,
					ChannelHandlerContextCache.getServerIpAddress(),
					ImConstants.DEFAULT_HEART_BEAT_GAP * 2,
					TimeUnit.SECONDS);
			
			ctx.writeAndFlush(resMsg);
			return;
		}
		
		ctx.close();
		LOGGER.error("Error: token check error, imMsg: {}", imMsg);
		throw new IllegalArgumentException("Error: token check error");
	}

}
