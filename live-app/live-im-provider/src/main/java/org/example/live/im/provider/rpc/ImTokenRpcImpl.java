package org.example.live.im.provider.rpc;

import org.apache.dubbo.config.annotation.DubboService;
import org.example.live.im.interfaces.ImTokenRpc;
import org.example.live.im.provider.service.ImTokenService;

import jakarta.annotation.Resource;

@DubboService
public class ImTokenRpcImpl implements ImTokenRpc {
	
	@Resource
	private ImTokenService imTokenService;

	@Override
	public String cressteImLoginToken(long userId, int appId) {
		return imTokenService.cressteImLoginToken(userId, appId);
	}

	@Override
	public Long getUserIdByToken(String token) {
		return imTokenService.getUserIdByToken(token);
	}

}
