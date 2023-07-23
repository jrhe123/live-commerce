package org.example.live.im.core.server.handler.impl;

import org.example.live.im.core.server.common.ImMsg;
import org.example.live.im.core.server.handler.SimplyHandler;
import org.example.live.im.dto.ImMsgBody;

import com.alibaba.fastjson.JSON;

import io.netty.channel.ChannelHandlerContext;

public class LoginMsgHandler implements SimplyHandler {

	@Override
	public void handler(ChannelHandlerContext ctx, ImMsg imMsg) {
		System.out.println("this is login message handler");
		
		byte[] body = imMsg.getBody();
		ImMsgBody imMsgBody = JSON.parseObject(new String(body), ImMsgBody.class);
		
		String token = imMsgBody.getToken(); 
		
		
		
		ctx.writeAndFlush(imMsg);
	}

}
