package org.example.live.im.core.server.starter;

import org.example.live.im.core.server.common.ChannelHandlerContextCache;
import org.example.live.im.core.server.common.ImMsgDecoder;
import org.example.live.im.core.server.common.ImMsgEncoder;
import org.example.live.im.core.server.handler.ImServerCoreHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.alibaba.cloud.commons.lang.StringUtils;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import jakarta.annotation.Resource;

@Configuration
public class NettyImServerStarter implements InitializingBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(NettyImServerStarter.class);

	// listen port
	@Value("${im.port}")
	private int port;
	
	@Resource
	private ImServerCoreHandler imServerCoreHandler;
	
	@Resource
	private Environment environment;
	
	

	// Netty is using NIO
	// start Netty, blind port
	public void startApplication() throws InterruptedException {

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

				// add encoder & decoder
				ch.pipeline().addLast(new ImMsgDecoder());
				ch.pipeline().addLast(new ImMsgEncoder());

				// add Netty handler
				ch.pipeline().addLast(imServerCoreHandler);
			}
		});

		// JVM close
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			// close thread pool
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}));
		
		// docker 启动中设置ENV
		// -DUBBO_IP_TO_REGISTRY=192.168.12.5
		// -DUBBO_PORT_TO_REGISTRY=9093
		
		// RUN AS -> RUN CONFIGURATION -> ADD ENV
		
//		String registryIp = environment.getProperty("DUBBO_IP_TO_REGISTRY");
//		String registryPort = environment.getProperty("DUBBO_PORT_TO_REGISTRY");
		String registryIp = "192.168.12.5";
		String registryPort = "9093";

		if (StringUtils.isEmpty(registryIp) || StringUtils.isEmpty(registryPort)) {
			throw new IllegalArgumentException("registryIp and registryPort is required");
		}
		
		ChannelHandlerContextCache.setServerIpAddress(registryIp + ":" + registryPort);
		ChannelFuture channelFuture = bootstrap.bind(port).sync();
		LOGGER.info(">>> [NettyImServerApplication] IM service started at port: {}", port);

		// 同步阻塞主线程, 实现服务长期开启的效果
		channelFuture.channel().closeFuture().sync();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Thread nettyServerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    startApplication();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        nettyServerThread.setName("live-im-server");
        nettyServerThread.start();
	}

}
