package org.example.live.api;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiWebApplication {
	
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(ApiWebApplication.class);
		application.setWebApplicationType(WebApplicationType.SERVLET);
		application.setDefaultProperties(Collections.singletonMap("server.port", "8081"));
		application.run(args);
	}
}
