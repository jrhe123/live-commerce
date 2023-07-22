package org.example.live.im.core.server.handler;

import org.example.live.im.core.server.common.ImMsg;

import io.netty.channel.ChannelHandlerContext;

public interface ImHandlerFactory {

	// 分发不同类型的handler
	void doMsgHandler(
			ChannelHandlerContext ctx, ImMsg imMsg
			);
}
