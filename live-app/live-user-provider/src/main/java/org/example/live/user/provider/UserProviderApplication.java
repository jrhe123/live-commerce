package org.example.live.user.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.example.live.user.contants.UserTagsEnum;
import org.example.live.user.dto.UserLoginDTO;
import org.example.live.user.provider.service.IUserPhoneService;
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
	
	@Resource
	private IUserPhoneService userPhoneService;

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(
				UserProviderApplication.class);
		application.setWebApplicationType(WebApplicationType.NONE);
		application.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		testUserLogin();
	}
	
	private void testUserLogin() {
		
		System.out.println("!!!!!!!!!!! test user login");
		System.out.println("!!!!!!!!!!! test user login");
		System.out.println("!!!!!!!!!!! test user login");
		System.out.println("!!!!!!!!!!! test user login");
		System.out.println("!!!!!!!!!!! test user login");
		System.out.println("!!!!!!!!!!! test user login");
		
		String phoneString = "16479291623";
		UserLoginDTO userLoginDTO = userPhoneService.login(phoneString);
				
//		System.out.println(userLoginDTO);
//		System.out.println(userPhoneService.queryByPhone(phoneString));
//		System.out.println(userPhoneService.queryByPhone(phoneString));
//				
//		System.out.println(userPhoneService.queryByUserId(userLoginDTO.getUserId()));
//		System.out.println(userPhoneService.queryByUserId(userLoginDTO.getUserId()));
	}
	
	private void testUser() {
//		long userId = 1001L;
		
//		System.out.println("add vip tag: " + userTagService.setTag(userId, UserTagsEnum.IS_VIP));
		
//		System.out.println("check vip tag exists: " + userTagService.containTag(userId, UserTagsEnum.IS_VIP));
		
//		System.out.println("cancel vip tag: " + userTagService.cancelTag(userId, UserTagsEnum.IS_VIP));
//		
//		System.out.println("check vip tag exists after cancel: " + userTagService.containTag(userId, UserTagsEnum.IS_VIP));
	}
}
