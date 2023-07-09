package org.example.live.framework.web.starter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(
        		streamUserInfoInterceptor()
        		).addPathPatterns("/**").excludePathPatterns("/error");
    }
    
    @Bean
    public StreamUserInfoInterceptor streamUserInfoInterceptor() {
        return new StreamUserInfoInterceptor();
    }

}
