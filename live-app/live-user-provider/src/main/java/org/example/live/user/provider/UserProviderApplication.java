package org.example.live.user.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.example.live.user.contants.UserTagsEnum;
import org.example.live.user.provider.service.IUserTagService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import jakarta.annotation.Resource;

@SpringBootApplication
@EnableDubbo
@EnableDiscoveryClient
public class UserProviderApplication implements CommandLineRunner {
	
	@Resource
	private IUserTagService userTagService;

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(
				UserProviderApplication.class);
		application.setWebApplicationType(WebApplicationType.NONE);
		application.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		long userId = 1001L;
		
//		System.out.println("add vip tag: " + userTagService.setTag(userId, UserTagsEnum.IS_VIP));
		
		System.out.println("check vip tag exists: " + userTagService.containTag(userId, UserTagsEnum.IS_VIP));
		
//		System.out.println("cancel vip tag: " + userTagService.cancelTag(userId, UserTagsEnum.IS_VIP));
//		
//		System.out.println("check vip tag exists after cancel: " + userTagService.containTag(userId, UserTagsEnum.IS_VIP));
	}
}
