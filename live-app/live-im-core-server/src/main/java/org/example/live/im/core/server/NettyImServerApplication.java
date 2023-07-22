package org.example.live.im.core.server;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyImServerApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NettyImServerApplication.class);

	// listen port
	private int port;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	// Netty is using NIO
	// start Netty, blind port
	public void startApplication(int port) throws InterruptedException {
		setPort(port);
		
		/**
		 * 
		 * bossGroup: handle "accept"
		 * workerGroup: handle "isReadable" & "isWritable"
		 * 
		 */
		NioEventLoopGroup bossGroup = new NioEventLoopGroup();
		NioEventLoopGroup workerGroup = new NioEventLoopGroup();

		// initialize Netty handler
		// server bootstrap
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup, workerGroup);
		bootstrap.channel(NioServerSocketChannel.class);
		bootstrap.childHandler(new ChannelInitializer<>() {

			@Override
			protected void initChannel(Channel ch) throws Exception {
				// logger
				LOGGER.info(">>> [NettyImServerApplication] Init im channel");
			}
		});
		
		// JVM close
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			// close thread pool
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}));
		
		ChannelFuture channelFuture = bootstrap.bind(port).sync();
		LOGGER.info(">>> [NettyImServerApplication] IM service started at port: {}", port);
		
		// 同步阻塞主线程, 实现服务长期开启的效果
		channelFuture.channel().closeFuture().sync();
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		NettyImServerApplication nettyImServerApplication = new NettyImServerApplication();
		nettyImServerApplication.startApplication(9090);	
	}
	
	
	
	
}
