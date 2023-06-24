package org.example.live.user.provider.config;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.nacos.shaded.io.grpc.netty.shaded.io.netty.util.internal.ThreadLocalRandom;

import jakarta.annotation.Resource;

/**
 * config the producer bean
 * @author jiaronghe
 *
 */
@Configuration
public class RocketMQProducerConfig {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RocketMQProducerConfig.class);

	@Resource
	private RocketMQProducerProperties producerProperties;
	
	@Value("${spring.application.name}")
	private String applicationName;
	
	@Bean
	public MQProducer mqProducer() {
		
		ThreadPoolExecutor asyncThreadPoolExecutor = new ThreadPoolExecutor(
				100, 150, 3, TimeUnit.MINUTES, new ArrayBlockingQueue<>(1000),
				new ThreadFactory() {
					@Override
					public Thread newThread(Runnable r) {
						Thread thread = new Thread(r);
						thread.setName(
								applicationName+":rmq-producer:"+ThreadLocalRandom.current().nextInt(1000));
						return thread;
					}
				}
				);
		
		
		DefaultMQProducer defaultMQProducer = new DefaultMQProducer();
		try {
			defaultMQProducer.setNamesrvAddr(producerProperties.getNameSrv());
			defaultMQProducer.setProducerGroup(producerProperties.getGroupName());
			defaultMQProducer.setRetryTimesWhenSendFailed(producerProperties.getRetryTimes());
			defaultMQProducer.setRetryTimesWhenSendAsyncFailed(producerProperties.getRetryTimes());
			defaultMQProducer.setRetryAnotherBrokerWhenNotStoreOK(true);
			
			// setup async thread-pool
			defaultMQProducer.setAsyncSenderExecutor(asyncThreadPoolExecutor);
			
			defaultMQProducer.start();
			
			LOGGER.info("RocketMQ producer started: nameSrv is {}", producerProperties.getNameSrv());
			
		} catch (MQClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return defaultMQProducer;
	}
}
