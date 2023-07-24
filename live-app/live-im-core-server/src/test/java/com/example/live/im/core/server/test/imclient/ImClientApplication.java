package com.example.live.im.core.server.test.imclient;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

import com.example.live.im.core.server.test.imclient.handler.ImClientHandler;

import jakarta.annotation.Resource;

@SpringBootTest
@ComponentScan(basePackages = {
		"com.example.live.im.core.server.test.**"
		})
@DubboComponentScan(basePackages = "com.example.live.im.core.server.test.**")
@ContextConfiguration(classes = ImClientApplication.class)
public class ImClientApplication {
	
	@Resource
	private ImClientHandler imClientHandler;
	
	
	@Test
    void Test() {
		System.out.println("call Test");
    }
}
