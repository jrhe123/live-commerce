package org.example.live.id.generate.provider.rpc;

import org.apache.dubbo.config.annotation.DubboService;
import org.example.live.id.generate.interfaces.IdGenerateRpc;
import org.example.live.id.generate.provider.service.IdGenerateService;

import jakarta.annotation.Resource;

@DubboService
public class IdGenerateRpcImpl implements IdGenerateRpc {
	
	@Resource
	private IdGenerateService idGenerateService;

	@Override
	public Long getSeqId(Integer id) {
		return idGenerateService.getSeqId(id);
	}

	@Override
	public Long getUnSeqId(Integer id) {
		return idGenerateService.getUnSeqId(id);
	}

}
