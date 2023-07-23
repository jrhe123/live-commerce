package imclient;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ImClientApplication {
	
	public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(ImClientApplication.class);
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        springApplication.run(args);
    }

//	public static void main(String[] args) throws InterruptedException {
//		
//		ImClientApplication clientApplication = new ImClientApplication();
//		clientApplication.startConnection("localhost", 8085);
//	}
//
//	private void startConnection(String address, int port) throws InterruptedException {
//		
//		EventLoopGroup clientGroup = new NioEventLoopGroup();
//		Bootstrap bootstrap = new Bootstrap();
//		bootstrap.group(clientGroup);
//		bootstrap.channel(NioSocketChannel.class);
//		bootstrap.handler(new ChannelInitializer<SocketChannel>() {
//
//			@Override
//			protected void initChannel(SocketChannel ch) throws Exception {
//				System.out.println("[Client] init connection built");
//				
//				// encoder & decoder
//				ch.pipeline().addLast(new ImMsgEncoder());
//				ch.pipeline().addLast(new ImMsgDecoder());
//				
//				// client receive handler
//				ch.pipeline().addLast(new ClientHandler());
//			}
//		});
//		
//		ChannelFuture channelFuture = bootstrap.connect(address, port).sync();
//		Channel channel = channelFuture.channel();
//		
//		// send message to server every 3 seconds
//		for(int i = 0; i < 100; i++) {
//			channel.writeAndFlush(
//					ImMsg.build(ImMsgCodeEnum.IM_LOGIN_MSG.getCode(), "login")
//					);
//			channel.writeAndFlush(
//					ImMsg.build(ImMsgCodeEnum.IM_LOGOUT_MSG.getCode(), "logout")
//					);
//			channel.writeAndFlush(
//					ImMsg.build(ImMsgCodeEnum.IM_HEARTBEAT_MSG.getCode(), "heartbeat")
//					);
//			channel.writeAndFlush(
//					ImMsg.build(ImMsgCodeEnum.IM_BIZ_MSG.getCode(), "business")
//					);
//			
//			
//			Thread.sleep(3000);
//		}
//	}
}
