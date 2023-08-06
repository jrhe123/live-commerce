import java.util.HashMap;
import java.util.Map;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.example.live.im.constants.AppIdEnum;
import org.example.live.im.constants.ImMsgCodeEnum;
import org.example.live.im.core.server.common.ImMsg;
import org.example.live.im.core.server.common.ImMsgDecoder;
import org.example.live.im.core.server.common.ImMsgEncoder;
import org.example.live.im.dto.ImMsgBody;
import org.example.live.im.interfaces.ImTokenRpc;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.live.im.core.server.test.imclient.handler.ClientHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 
 * mvn test
 * 
 * @author jiaronghe
 *
 */
@SpringBootTest
@ComponentScan(basePackages = {
		"com.example.live.im.core.server.test.**"
		})
@DubboComponentScan(basePackages = "com.example.live.im.core.server.test.**")
@ContextConfiguration(classes = NettyServerTest.class)
public class NettyServerTest {
	
	@DubboReference
    private ImTokenRpc imTokenRpc;
	
	private static Bootstrap bootstrap;

//    private static EventLoopGroup bossGroup;
//    private static EventLoopGroup workerGroup;
//    private static ChannelFuture channelFuture;

    @BeforeClass
    public static void setup() throws InterruptedException {
//    	EventLoopGroup clientGroup = new NioEventLoopGroup();    
//    	
//        bootstrap = new Bootstrap();
//        bootstrap.group(clientGroup);
//        bootstrap.channel(NioSocketChannel.class);
//        
//        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
//            @Override
//            protected void initChannel(SocketChannel ch) throws Exception {
//                System.out.println("初始化连接建立");
//                ch.pipeline().addLast(new ImMsgDecoder());
//                ch.pipeline().addLast(new ImMsgEncoder());
//                ch.pipeline().addLast(new ClientHandler());
//            }
//        });        
    }

    @AfterClass
    public static void teardown() throws InterruptedException {
    	
    }

    @Test
    public void testSomething() {
        /**
         * 
         * do the test here
         * 
         */
    	
    	System.out.println("!!!!!!!!!!!!!!!! called test");
    	
//    	Map<Long, Channel> userIdChannelMap = new HashMap<>();
//        for (int i = 0; i < 10; i++) {
//            Long userId = 200000L + i;
//            String token = imTokenRpc.createImLoginToken(userId, AppIdEnum.LIVE_BIZ_APP.getCode());
//            
//            System.out.println("!!!!!!!!!!!!!!!! generated token: " + token);
//            
//            ChannelFuture channelFuture = null;
//            try {
//                channelFuture = bootstrap.connect("localhost", 8085).sync();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            Channel channel = channelFuture.channel();
//            ImMsgBody imMsgBody = new ImMsgBody();
//            imMsgBody.setAppId(AppIdEnum.LIVE_BIZ_APP.getCode());
//            imMsgBody.setToken(token);
//            imMsgBody.setUserId(userId);
//            
//            
//            ImMsg loginMsg = ImMsg.build(ImMsgCodeEnum.IM_LOGIN_MSG.getCode(), JSON.toJSONString(imMsgBody));
//            channel.writeAndFlush(loginMsg);
//            userIdChannelMap.put(userId, channel);
//        }
//        
//        
//        while (true) {
//            for (Long userId : userIdChannelMap.keySet()) {
//                ImMsgBody bizBody = new ImMsgBody();
//                bizBody.setAppId(AppIdEnum.LIVE_BIZ_APP.getCode());
//                bizBody.setUserId(userId);
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("userId", userId);
//                
//                
//                // objectId -> indicate which IM server to send back
//                jsonObject.put("objectId", 1001101L);
//                
//                
//                jsonObject.put("content", "你好,我是" + userId);
//                bizBody.setData(JSON.toJSONString(jsonObject));
//                
//                
//                System.out.println("!!!!!!!!!!!!!!!! send biz message");
//                System.out.println("!!!!!!!!!!!!!!!! send biz message");
//                System.out.println("!!!!!!!!!!!!!!!! send biz message");
//                
//                
//                ImMsg heartBeatMsg = ImMsg.build(ImMsgCodeEnum.IM_BIZ_MSG.getCode(), JSON.toJSONString(bizBody));
//                userIdChannelMap.get(userId).writeAndFlush(heartBeatMsg);
//            }
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
    }
}