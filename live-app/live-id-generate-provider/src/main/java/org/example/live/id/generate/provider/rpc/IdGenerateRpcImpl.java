package org.example.live.id.generate.provider.rpc;

import org.apache.dubbo.config.annotation.DubboService;
import org.example.live.id.generate.interfaces.IdGenerateRpc;

@DubboService
public class IdGenerateRpcImpl implements IdGenerateRpc {

	@Override
	public Long getSeqId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getUnSeqId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
