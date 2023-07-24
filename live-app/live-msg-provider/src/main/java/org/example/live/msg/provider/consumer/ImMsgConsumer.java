package org.example.live.msg.provider.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.example.live.common.interfaces.topic.ImCoreServerProviderTopicNames;
import org.example.live.framework.mq.starter.properties.RocketMQConsumerProperties;
import org.example.live.im.dto.ImMsgBody;
import org.example.live.msg.provider.consumer.handler.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import jakarta.annotation.Resource;

@Component
public class ImMsgConsumer implements InitializingBean {
	
	
    private static final Logger LOGGER = LoggerFactory.getLogger(ImMsgConsumer.class);
    
    @Resource
    private RocketMQConsumerProperties rocketMQConsumerProperties;
    
    @Resource
    private MessageHandler singleMessageHandler;
    
    
    // 记录每个用户连接的im服务器地址，然后根据im服务器的连接地址去做具体机器的调用
    // 基于mq广播思路去做，可能会有消息风暴发生，100台im机器，99%的mq消息都是无效的
    
    // 加入一个叫路由层的设计，router中转的设计，router就是一个dubbo的rpc层
    // A--> B 
    // im-core-server -> msg-provider(持久化) -> im-core-server -> 通知到b
	@Override
	public void afterPropertiesSet() throws Exception {
		DefaultMQPushConsumer mqPushConsumer = new DefaultMQPushConsumer();
        // ** 老版本中会开启，新版本的mq不需要使用到
        mqPushConsumer.setVipChannelEnabled(false);
        // set properties
        mqPushConsumer.setNamesrvAddr(rocketMQConsumerProperties.getNameSrv());
        mqPushConsumer.setConsumerGroup(rocketMQConsumerProperties.getGroupName() + "_" + ImMsgConsumer.class.getSimpleName());
        
        
        //一次从broker中拉取10条消息到本地内存当中进行消费 !!!
        mqPushConsumer.setConsumeMessageBatchMaxSize(10);
        mqPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        
        
        //监听im发送过来的业务消息topic "LIVE_IM_BIZ_MSG_TOPIC" !!!
        mqPushConsumer.subscribe(
        		ImCoreServerProviderTopicNames.LIVE_IM_BIZ_MSG_TOPIC, "");
        mqPushConsumer.setMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            for (MessageExt msg : msgs) {
            	// parse it
                ImMsgBody imMsgBody = JSON.parseObject(
                		new String(msg.getBody()), ImMsgBody.class);
                
                System.out.println("!!! consumer received !!!: ");
                System.out.println("!!! consumer received !!!: ");
                System.out.println("!!! consumer received !!!: ");
                System.out.println("!!! consumer received !!!: ");
                System.out.println("!!! consumer received !!!: " + imMsgBody);
                
                // execute the message: the business logic here
                singleMessageHandler.onMsgReceive(imMsgBody);
            }
            
            // return consume success !!!
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        
        
        
        mqPushConsumer.start();
        LOGGER.info("!!! MQ consumer started !!!, namesrv is {}", rocketMQConsumerProperties.getNameSrv());
		
	}

}
