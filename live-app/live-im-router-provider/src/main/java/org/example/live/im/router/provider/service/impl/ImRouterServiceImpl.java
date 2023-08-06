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
//		String bindAddress = stringRedisTemplate.opsForValue()
//				.get(ImCoreServerConstants.IM_BIND_IP_KEY + 
//						imMsgBody.getAppId() + imMsgBody.getUserId());
		
		// im-core-server: ip address & port
        String bindAddress = "192.168.12.5:9093";
		
		if (StringUtils.isEmpty(bindAddress)) {
            return false;
        }
		
		/**
		 * 
		 * !!! IMPORTANT !!!
		 * 设置ip，在cluster invoker 中使用ip addr
		 * 利用对应的rpc ip 找到正确的 im-core-server
		 * 
		 */
        RpcContext.getContext().set("ip", bindAddress);
        
        
        routerHandlerRpc.sendMsg(imMsgBody);
        return true;
	}

}
