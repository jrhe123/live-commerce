package org.example.live.msg.provider.config;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.alibaba.nacos.shaded.io.grpc.netty.shaded.io.netty.util.internal.ThreadLocalRandom;

public class ThreadPoolManager {

	
	public static ThreadPoolExecutor commonAsyncPool = 
			/**
			 * Thread pool for sms async
			 * 
			 * corePoolSize: 2
			 * maximumPoolSize: 8
			 * keepAliveTime: 3 seconds
			 */
			new ThreadPoolExecutor(2, 8, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100),
					new ThreadFactory() {
						
						@Override
						public Thread newThread(Runnable r) {
							Thread newThread = new Thread(r);
							newThread.setName("commonAsyncPool - " + ThreadLocalRandom.current().nextInt(1000));
							return newThread;
						}
					});
}
