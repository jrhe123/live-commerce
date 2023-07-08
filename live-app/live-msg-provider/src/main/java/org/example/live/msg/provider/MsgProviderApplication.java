package org.example.live.msg.provider;

import java.util.Scanner;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.example.live.msg.dto.MsgCheckDTO;
import org.example.live.msg.enums.MsgSendResultEnum;
import org.example.live.msg.provider.service.ISmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import jakarta.annotation.Resource;

@SpringBootApplication
@EnableDubbo
@EnableDiscoveryClient
public class MsgProviderApplication implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(MsgProviderApplication.class);

	@Resource
	private ISmsService smsService;
	
	
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(MsgProviderApplication.class);
		application.setWebApplicationType(WebApplicationType.NONE);
		application.run(args);
	}


	@Override
	public void run(String... args) throws Exception {
//		String phoneString = "6479291623";
//		MsgSendResultEnum msgSendResultEnum = 
//				smsService.sendLoginCode(phoneString);
//		System.out.println("msgSendResultEnum: " + msgSendResultEnum);
//		
//		while (true) {
//			System.out.println("please enter code");
//			Scanner scanner = new Scanner(System.in);
//			int code = scanner.nextInt();
//			
//			MsgCheckDTO checkDTO = smsService.checkLoginCode(phoneString, code);
//			System.out.println("checkDTO: " + checkDTO);
//		}
	}
}
