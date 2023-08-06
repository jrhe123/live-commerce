package org.example.live.im.core.server.handler.impl;

import java.util.concurrent.TimeUnit;

import org.example.live.framework.redis.starter.key.ImCoreServerProviderCacheKeyBuilder;
import org.example.live.im.constants.ImConstants;
import org.example.live.im.constants.ImMsgCodeEnum;
import org.example.live.im.core.server.common.ImContextUtils;
import org.example.live.im.core.server.common.ImMsg;
import org.example.live.im.core.server.handler.SimplyHandler;
import org.example.live.im.dto.ImMsgBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.example.live.im.core.server.interfaces.constants.ImCoreServerConstants;

import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.Resource;

@Component
public class HeartBeatImMsgHandler implements SimplyHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(HeartBeatImMsgHandler.class);

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private ImCoreServerProviderCacheKeyBuilder cacheKeyBuilder;

    @Override
    public void handler(ChannelHandlerContext ctx, ImMsg imMsg) {
    	
        // validate
        Long userId = ImContextUtils.getUserId(ctx);
        Integer appId = ImContextUtils.getAppId(ctx);
        
        
        
        if (userId == null || appId == null) {
            LOGGER.error("attr error,imMsg is {}", imMsg);
            ctx.close();
            throw new IllegalArgumentException("attr is error");
        }
        
        
        //心跳包record记录，redis存储心跳记录
        String redisKey = cacheKeyBuilder.buildImLoginTokenKey(userId, appId);
        
        
        
        this.recordOnlineTime(userId, redisKey);
        this.removeExpireRecord(redisKey);
        // 5分钟，续命
        redisTemplate.expire(redisKey, 5, TimeUnit.MINUTES);
        
        
        
        //延长用户之前保存的ip绑定记录时间, 2 * heartbeat time elapse
        stringRedisTemplate.expire(ImCoreServerConstants.IM_BIND_IP_KEY + appId + userId, 
        		ImConstants.DEFAULT_HEART_BEAT_GAP * 2, TimeUnit.SECONDS);
        
        
        
        // response msg 返回数据给客户端
        ImMsgBody msgBody = new ImMsgBody();
        msgBody.setUserId(userId);
        msgBody.setAppId(appId);
        msgBody.setData("true");
        ImMsg respMsg = ImMsg.build(
        		ImMsgCodeEnum.IM_HEARTBEAT_MSG.getCode(),
        		JSON.toJSONString(msgBody)
        		);
        LOGGER.info("[HeartBeatImMsgHandler] imMsg is {}", imMsg);
        
        
        
        ctx.writeAndFlush(respMsg);
    }

    /**
     * 根据score删除
     * 
     * 30sec * 2 = 60sec (两次心跳)
     * 清理掉过期不在线的用户留下的心跳记录(在两次心跳包的发送间隔中，如果没有重新更新score值，就会导致被删除)
     *
     * @param redisKey
     */
    private void removeExpireRecord(String redisKey) {
        redisTemplate.opsForZSet().removeRangeByScore(
        		redisKey, 0, 
        		System.currentTimeMillis() - ImConstants.DEFAULT_HEART_BEAT_GAP * 1000 * 2);
    }

    /**
     * 记录用户最近一次心跳时间到zSet上
     *
     * @param userId
     * @param redisKey
     */
    private void recordOnlineTime(Long userId, String redisKey) {
        redisTemplate.opsForZSet().add(redisKey, userId, System.currentTimeMillis());
    }


}
