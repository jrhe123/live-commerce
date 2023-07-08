package org.example.live.msg.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDubbo
@EnableDiscoveryClient
public class MsgProviderApplication implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(MsgProviderApplication.class);

	
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(MsgProviderApplication.class);
		application.setWebApplicationType(WebApplicationType.NONE);
		application.run(args);
	}


	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
