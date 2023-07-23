package org.example.live.framework.mq.starter.producer;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.example.live.framework.mq.starter.properties.RocketMQProducerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.Resource;

@Configuration
public class RocketMQProducerConfig {

	@Resource
    private RocketMQProducerProperties rocketMQProducerProperties;
	
	@Bean
    public MQProducer mqProducer() {
		
        ThreadPoolExecutor asyncThreadPool = new ThreadPoolExecutor(
        		Runtime.getRuntime().availableProcessors() * 2, 100, 30, TimeUnit.SECONDS, 
        		new ArrayBlockingQueue<>(2000), 
        		new ThreadFactory() {
			            @Override
			            public Thread newThread(Runnable r) {
			                return new Thread(r, "rocketmq-async-thread-" + new Random().ints().toString());
			            }
			        }
        		);
        
        
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer();
        defaultMQProducer.setProducerGroup(rocketMQProducerProperties.getGroupName());
        defaultMQProducer.setNamesrvAddr(rocketMQProducerProperties.getNameSrv());
        defaultMQProducer.setRetryTimesWhenSendFailed(rocketMQProducerProperties.getRetryTimes());
        defaultMQProducer.setRetryTimesWhenSendAsyncFailed(rocketMQProducerProperties.getRetryTimes());
        defaultMQProducer.setRetryAnotherBrokerWhenNotStoreOK(true);
        defaultMQProducer.setAsyncSenderExecutor(asyncThreadPool);
        
        
        try {
            defaultMQProducer.start();
            System.out.println("=============== !!! mq producer started, namesrv is " + 
            		rocketMQProducerProperties.getNameSrv() + " !!! ==================");
        } catch (MQClientException e) {
            throw new RuntimeException(e);
        }
        
        
        return defaultMQProducer;
    }
}
