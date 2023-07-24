package org.example.live.im.router.provider.service.impl;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.example.live.im.dto.ImMsgBody;
import org.example.live.im.router.provider.service.ImRouterService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.example.live.im.core.server.interfaces.constants.ImCoreServerConstants;
import com.example.live.im.core.server.interfaces.rpc.IRouterHandlerRpc;

import jakarta.annotation.Resource;

@Service
public class ImRouterServiceImpl implements ImRouterService {
	
    @DubboReference
    private IRouterHandlerRpc routerHandlerRpc;
    
    @Resource
    private StringRedisTemplate stringRedisTemplate;

	@Override
	public boolean sendMsg(ImMsgBody imMsgBody) {
		String bindAddress = stringRedisTemplate.opsForValue()
				.get(ImCoreServerConstants.IM_BIND_IP_KEY + 
						imMsgBody.getAppId() + imMsgBody.getUserId());
        
		
		if (StringUtils.isEmpty(bindAddress)) {
            return false;
        }
		
        RpcContext.getContext().set("ip", bindAddress);
        
        
        routerHandlerRpc.sendMsg(imMsgBody);
        return true;
	}

}
