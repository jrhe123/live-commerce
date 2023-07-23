package imclient;



import org.example.live.im.core.server.common.ImMsg;
import org.example.live.im.core.server.common.ImMsgDecoder;
import org.example.live.im.core.server.common.ImMsgEncoder;
import org.example.live.im.interfaces.ImMsgCodeEnum;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ImClientApplication {

	public static void main(String[] args) throws InterruptedException {
		
		ImClientApplication clientApplication = new ImClientApplication();
		clientApplication.startConnection("localhost", 9090);
	}

	private void startConnection(String address, int port) throws InterruptedException {
		
		EventLoopGroup clientGroup = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(clientGroup);
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				System.out.println("[Client] init connection built");
				
				// encoder & decoder
				ch.pipeline().addLast(new ImMsgEncoder());
				ch.pipeline().addLast(new ImMsgDecoder());
				
				// client receive handler
				ch.pipeline().addLast(new ClientHandler());
			}
		});
		
		ChannelFuture channelFuture = bootstrap.connect(address, port).sync();
		Channel channel = channelFuture.channel();
		
		// send message to server every 3 seconds
		for(int i = 0; i < 100; i++) {
			channel.writeAndFlush(
					ImMsg.build(ImMsgCodeEnum.IM_LOGIN_MSG.getCode(), "login")
					);
			channel.writeAndFlush(
					ImMsg.build(ImMsgCodeEnum.IM_LOGOUT_MSG.getCode(), "logout")
					);
			channel.writeAndFlush(
					ImMsg.build(ImMsgCodeEnum.IM_HEARTBEAT_MSG.getCode(), "heartbeat")
					);
			channel.writeAndFlush(
					ImMsg.build(ImMsgCodeEnum.IM_BIZ_MSG.getCode(), "business")
					);
			
			
			Thread.sleep(3000);
		}
	}
}
