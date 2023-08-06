package org.example.live.im.core.server.rpc;

import org.apache.dubbo.config.annotation.DubboService;
import org.example.live.im.core.server.service.IRouterHandlerService;
import org.example.live.im.dto.ImMsgBody;

import com.example.live.im.core.server.interfaces.rpc.IRouterHandlerRpc;

import jakarta.annotation.Resource;

@DubboService
public class RouterHandlerRpcImpl implements IRouterHandlerRpc {
	
	@Resource
    private IRouterHandlerService routerHandlerService;

	
	@Override
	public void sendMsg(ImMsgBody imMsgBody) {
		/**
		 * 
		 * 发送到client B (im core server)
		 * send to client B now
		 */
		routerHandlerService.onReceive(imMsgBody);
	}

}
