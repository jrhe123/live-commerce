package org.example.live.im.core.server.handler;

import org.example.live.im.core.server.common.ImMsg;

import io.netty.channel.ChannelHandlerContext;

public interface SimplyHandler {

	void handler(
			ChannelHandlerContext ctx, ImMsg imMsg
			);
}
