package org.example.live.framework.web.starter;

import org.example.live.common.interfaces.enums.GatewayHeaderEnum;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.nacos.api.utils.StringUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class StreamUserInfoInterceptor implements HandlerInterceptor {

	// intercept all the web requests
    @Override
    public boolean preHandle(HttpServletRequest request, 
    		HttpServletResponse response, Object handler) throws Exception {
    	
        String userIdStr = request.getHeader(GatewayHeaderEnum.USER_LOGIN_ID.getName());
        // check params, if userId is null
        // if there's no userId, MEANS it's a white-listed url
        if (StringUtils.isEmpty(userIdStr)) {
            return true;
        }
        // if userId exists, save it into local thread
        StreamRequestContext.set(
        		RequestConstants.STREAM_USER_ID, Long.valueOf(userIdStr));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, 
    		HttpServletResponse response, Object handler,
    		ModelAndView modelAndView) throws Exception {
    	StreamRequestContext.clear();
    }
}
