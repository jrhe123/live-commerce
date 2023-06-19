package org.example.live.user.provider.rpc;

import org.apache.dubbo.config.annotation.DubboService;
import org.example.live.user.interfaces.IUserRpc;

@DubboService
public class UserRpcImpl implements IUserRpc {

	@Override
	public String test() {
		System.out.println("test dubbo service");
		return "OK";
	}

}
