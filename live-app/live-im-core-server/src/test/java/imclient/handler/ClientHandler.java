package imclient.handler;

import org.example.live.im.core.server.common.ImMsg;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ImMsg imMsg = (ImMsg) msg;
		
		System.out.println("[ClientHandler] server reply result: " + imMsg);
	}
}
