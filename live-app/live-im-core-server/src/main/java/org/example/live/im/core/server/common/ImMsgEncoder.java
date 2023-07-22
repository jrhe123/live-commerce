package org.example.live.im.core.server.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ImMsgEncoder extends MessageToByteEncoder {

	
	// implment abstract encode method
	@Override
	protected void encode(
			ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {

		ImMsg imMsg = (ImMsg) msg;
		
		out.writeShort(imMsg.getMagic());
		out.writeInt(imMsg.getCode());
		out.writeInt(imMsg.getLen());
		out.writeBytes(imMsg.getBody());
		
//		ctx.writeAndFlush(out);
	}

}
