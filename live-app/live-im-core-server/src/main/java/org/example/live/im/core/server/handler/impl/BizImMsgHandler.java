package org.example.live.im.core.server.handler.impl;

import org.example.live.im.core.server.common.ImMsg;
import org.example.live.im.core.server.handler.SimplyHandler;

import io.netty.channel.ChannelHandlerContext;

public class BizImMsgHandler implements SimplyHandler {

	@Override
	public void handler(ChannelHandlerContext ctx, ImMsg imMsg) {
		System.out.println("this is biz message handler");
		
		ctx.writeAndFlush(imMsg);
	}

}