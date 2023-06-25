package org.example.live.user.provider.rpc;

import org.apache.dubbo.config.annotation.DubboService;
import org.example.live.user.contants.UserTagsEnum;
import org.example.live.user.interfaces.IUserTagRpc;

@DubboService
public class UserTagRpcImpl implements IUserTagRpc {

	@Override
	public boolean setTag(Long userId, UserTagsEnum userTagsEnum) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cancelTag(Long userId, UserTagsEnum userTagsEnum) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containTag(Long userId, UserTagsEnum userTagsEnum) {
		// TODO Auto-generated method stub
		return false;
	}

}
