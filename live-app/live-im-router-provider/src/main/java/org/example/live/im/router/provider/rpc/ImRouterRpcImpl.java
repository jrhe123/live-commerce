package org.example.live.im.router.provider.rpc;

import org.apache.dubbo.config.annotation.DubboService;
import org.example.live.im.dto.ImMsgBody;
import org.example.live.im.router.interfaces.rpc.ImRouterRpc;
import org.example.live.im.router.provider.service.ImRouterService;

import jakarta.annotation.Resource;

@DubboService
public class ImRouterRpcImpl implements ImRouterRpc {
	
	@Resource
    private ImRouterService routerService;

	
	
	@Override
	public boolean sendMsg(ImMsgBody imMsgBody) {
		return routerService.sendMsg(imMsgBody);
	}

}
