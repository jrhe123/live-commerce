package org.example.live.im.core.server.handler;

import org.example.live.im.core.server.common.ImMsg;
import org.example.live.im.core.server.handler.impl.ImHandlerFactoryImpl;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ImServerCoreHandler extends SimpleChannelInboundHandler {
	
	
	private ImHandlerFactory imHandlerFactory = new ImHandlerFactoryImpl();
	

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

}
