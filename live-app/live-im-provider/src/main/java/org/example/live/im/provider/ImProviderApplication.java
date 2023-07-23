package org.example.live.im.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.example.live.im.constants.AppIdEnum;
import org.example.live.im.provider.service.ImTokenService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.Resource;


@SpringBootApplication
@EnableDubbo
public class ImProviderApplication implements CommandLineRunner {
	
	@Resource
	private ImTokenService imTokenService;

	
	public static void main(String[] args) {
		
        SpringApplication springApplication =
        		new SpringApplication(ImProviderApplication.class);
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        springApplication.run(args);
    }

	@Override
	public void run(String... args) throws Exception {
//		long userId = 109213231L;
//		
//		String token = imTokenService.createImLoginToken(
//				userId, AppIdEnum.LIVE_BIZ_APP.getCode());
//		
//		Long userIdByToken = imTokenService.getUserIdByToken(token);
//		
//		System.out.println(">>>>>>>");
//		System.out.println(">>>>>>> userId: " + userId);
//		System.out.println(">>>>>>> retrived from token: " + userIdByToken);
	}

}
