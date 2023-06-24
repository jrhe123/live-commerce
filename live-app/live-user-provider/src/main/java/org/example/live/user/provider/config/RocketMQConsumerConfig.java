package org.example.live.user.provider.config;

import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.example.live.framework.redis.starter.key.UserProviderCacheKeyBuilder;
import org.example.live.user.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson.JSON;

import groovyjarjarantlr4.v4.parse.ANTLRParser.finallyClause_return;
import jakarta.annotation.Resource;

/**
 * config the consumer bean
 * 
 * @author jiaronghe
 *
 */
@Configuration
public class RocketMQConsumerConfig implements InitializingBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(RocketMQConsumerConfig.class);

	@Resource
	private RocketMQConsumerProperties consumerProperties;
	
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Resource
	private UserProviderCacheKeyBuilder userProviderCacheKeyBuilder;

	@Override
	public void afterPropertiesSet() throws Exception {
		initConsumer();
	}

	public void initConsumer() {

		try {
			DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer();
			defaultMQPushConsumer.setNamesrvAddr(consumerProperties.getNameSrv());
			defaultMQPushConsumer.setConsumerGroup(consumerProperties.getGroupName());
			defaultMQPushConsumer.setConsumeMessageBatchMaxSize(1);
			defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
			defaultMQPushConsumer.subscribe("user-update-cache", "*");

			defaultMQPushConsumer.setMessageListener(new MessageListenerConcurrently() {
				@Override
				public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
						ConsumeConcurrentlyContext context) {
					
					System.out.println("!!!!!!!!!!");
					System.out.println("!!!!!!!!!!");
					System.out.println("!!!!!!!!!!");
					System.out.println("!!!!!!!!!!");
					System.out.println("CONSUMER received: NOW we delete the redis cache again");
					String msgString = new String(msgs.get(0).getBody());
					UserDTO userDTO = JSON.parseObject(
							msgString, 
							UserDTO.class);
					
					if (userDTO == null || userDTO.getUserId() == null) {
						LOGGER.error("Consumer: user id is null {}", msgString);
						return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
					}
					redisTemplate.delete(
							userProviderCacheKeyBuilder.buildUserInfoKey(userDTO.getUserId())
							);					
					return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
				}
			});

			defaultMQPushConsumer.start();

			LOGGER.info("RocketMQ consumer started: nameSrv is {}", consumerProperties.getNameSrv());
		} catch (MQClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
