package org.example.live.im.core.server.handler;

import org.example.live.im.core.server.common.ChannelHandlerContextCache;
import org.example.live.im.core.server.common.ImContextUtils;
import org.example.live.im.core.server.common.ImMsg;
import org.example.live.im.core.server.handler.impl.ImHandlerFactoryImpl;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import jakarta.annotation.Resource;

@Component
public class ImServerCoreHandler extends SimpleChannelInboundHandler {
	
	@Resource
	private ImHandlerFactory imHandlerFactory;
	

	@Override
	protected void channelRead0(
			ChannelHandlerContext ctx, Object msg) throws Exception {
		
		if (!(msg instanceof ImMsg)) {
			throw new IllegalArgumentException("Error msg: msg is: " + msg);
		}
		
		ImMsg imMsg = (ImMsg) msg;
		// int code = imMsg.getCode();
		
		
		// depends on the code, we send it to different handlers
		imHandlerFactory.doMsgHandler(ctx, imMsg);
		
		/**
		 * 
		 * login message:
		 * 1. token validate
		 * 2. channel & userId connect
		 * 
		 */
		
		
		/**
		 * 
		 * logout message:
		 * 1. when we disconnect im service
		 * 2. release resource
		 * 
		 */
		
		/**
		 * 
		 * business message:
		 * 1. connect front-end & back-end
		 * 2. message data
		 * 
		 */
		
		/**
		 * 
		 * heart-beat message
		 * 1. check application online (scheduler)
		 * 
		 */		
	}
	
	/**
	 * 
	 * 正常/意外断线，都会被触发
	 * 
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		
		Long userId = ImContextUtils.getUserId(ctx);
		
		// remove ChannelHandlerContext from the RAM, prevent leaking
        if (userId != null) {
            ChannelHandlerContextCache.remove(userId);
        }
	}

}
