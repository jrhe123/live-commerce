package org.example.live.framework.redis.starter.key;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
@Conditional(RedisKeyLoadMatch.class)
public class ImCoreServerProviderCacheKeyBuilder extends RedisKeyBuilder {

	private static String IM_ONLINE_ZSET = "imOnlineZset";
	
	/**
	 * 
	 * 分组
	 * redis zet: split
	 * 
	 * live-im-core-server:heartbeat:9999:zset
	 * 
	 * @param userId
	 * @param appId
	 * @return
	 */
	public String buildImLoginTokenKey(Long userId, Integer appId) {
		return super.getPrefix() + IM_ONLINE_ZSET + 
				super.getSplitItem() + appId + 
				super.getSplitItem() + userId % 10000;
	}
}
