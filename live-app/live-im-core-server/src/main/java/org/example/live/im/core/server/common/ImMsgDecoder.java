package org.example.live.im.core.server.common;

import java.util.List;

import org.example.live.im.interfaces.ImConstants;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class ImMsgDecoder extends ByteToMessageDecoder {
	
	private final int BASE_LEN = 2 + 4 + 4;
	/**
	 * magic: short -> 2
	 * code: int -> 4
	 * len: int -> 4
	 * body: unknown
	 * 
	 * so the length must be greater than 2 + 4 + 4
	 */

	@Override
	protected void decode(
			ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		
		// validate data length & magic
		if (in.readableBytes() >= BASE_LEN) {
			
			/**
			 * 顺序 depends on encode order
			 * 1. magic
			 * 2. code
			 * 3. len
			 * 4. body
			 */
			// check magic
			if ((in.readShort()) != ImConstants.DEFAULT_MAGIC) {
				// if magic is invalid, we close the channel and return
				ctx.close();
				return;
			}
			
			int code = in.readInt();
			int len = in.readInt();
			
			if (in.readableBytes() < len) {
				// if body is less then "len", we close the channel and return
				ctx.close();
				return;
			}
			
			byte[] body = new byte[len];
			in.readBytes(body);
			
			ImMsg imMsg = new ImMsg();
			imMsg.setCode(code);
			imMsg.setLen(len);
			imMsg.setBody(body);
			
			// return imMsg
			out.add(imMsg);
		}
		
		
		// convert bytebuf to imMsg
	}

}
