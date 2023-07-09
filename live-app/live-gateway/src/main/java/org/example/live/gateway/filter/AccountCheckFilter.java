package org.example.live.gateway.filter;

import java.util.List;

import org.apache.dubbo.config.annotation.DubboReference;
import org.example.live.account.interfaces.IAccountTokenRPC;
import org.example.live.common.interfaces.enums.GatewayHeaderEnum;
import org.example.live.gateway.properties.GatewayApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;

import com.alibaba.nacos.api.utils.StringUtils;

import jakarta.annotation.Resource;
import reactor.core.publisher.Mono;

import static io.netty.handler.codec.http.cookie.CookieHeaderNames.MAX_AGE;
import static org.springframework.web.cors.CorsConfiguration.ALL;

@Component
public class AccountCheckFilter implements GlobalFilter, Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountCheckFilter.class);

    @DubboReference
    private IAccountTokenRPC accountTokenRPC;
    
    @Resource
    private GatewayApplicationProperties gatewayApplicationProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    	// request url, check if it's exists, if not rejected
        ServerHttpRequest request = exchange.getRequest();
        String reqUrl = request.getURI().getPath();
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders headers = response.getHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:3000");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST, GET");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*");
        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, ALL);
        headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, MAX_AGE);

        
        
        if (StringUtils.isEmpty(reqUrl)) {
            return Mono.empty();
        }
        // check url, if it's exists in the white-list, if yes, we skip the token validate
        List<String> notCheckUrlList = gatewayApplicationProperties.getNotCheckUrlList();
        for (String notCheckUrl : notCheckUrlList) {
            if (reqUrl.startsWith(notCheckUrl)) {
                LOGGER.info("no check token, white-listed");
                // pass it to next directly
                return chain.filter(exchange);
            }
        }
        
        
        // if it's not exist in the white-list, we extract token from cookie
        // and then validate token
        List<HttpCookie> httpCookieList = request.getCookies().get("tk");
        if (CollectionUtils.isEmpty(httpCookieList)) {
            LOGGER.error("no tk in the cookies, rejected");
            return Mono.empty();
        }
        String qiyuTokenCookieValue = httpCookieList.get(0).getValue();
        if (StringUtils.isEmpty(qiyuTokenCookieValue) || 
        		StringUtils.isEmpty(qiyuTokenCookieValue.trim())) {
            LOGGER.error("tk is empty value, rejected");
            return Mono.empty();
        }
        
        
        // if token exists, we use RPC to validate the token
        // if it's valid, we convert it to userId, and pass it to next
        Long userId = accountTokenRPC.getUserIdByToken(qiyuTokenCookieValue);
        // if token  is invalid, we reject the request
        // log the error
        if (userId == null) {
            LOGGER.error("invalid token, rejected");
            return Mono.empty();
        }
        
        
        // gateway --(header)--> springboot-web(interceptor-->get header)
        ServerHttpRequest.Builder builder = request.mutate();
        // save userId into header
        builder.header(
        		GatewayHeaderEnum.USER_LOGIN_ID.getName(), String.valueOf(userId));
        
        
        
        return chain.filter(exchange.mutate().request(builder.build()).build());
    }

    @Override
    public int getOrder() {
    	// set precedence: 0 is highest
        return 0;
    }
}
