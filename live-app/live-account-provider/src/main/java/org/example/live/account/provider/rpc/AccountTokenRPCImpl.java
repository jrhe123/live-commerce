package org.example.live.account.provider.rpc;

import org.apache.dubbo.config.annotation.DubboService;
import org.example.live.account.interfaces.IAccountTokenRPC;
import org.example.live.accout.provider.service.IAccountTokenService;

import jakarta.annotation.Resource;

@DubboService
public class AccountTokenRPCImpl implements IAccountTokenRPC {
	
	@Resource
	private IAccountTokenService accountTokenService;

	@Override
	public String createAndSaveLoginToken(Long userId) {
		return accountTokenService.createAndSaveLoginToken(userId);
	}

	@Override
	public Long getUserIdByToken(String tokenKey) {
		return accountTokenService.getUserIdByToken(tokenKey);
	}

}
