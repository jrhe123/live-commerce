package org.example.live.im.core.server.service.impl;

import org.example.live.im.constants.ImMsgCodeEnum;
import org.example.live.im.core.server.common.ChannelHandlerContextCache;
import org.example.live.im.core.server.common.ImMsg;
import org.example.live.im.core.server.service.IRouterHandlerService;
import org.example.live.im.dto.ImMsgBody;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import io.netty.channel.ChannelHandlerContext;

@Service
public class RouterHandlerServiceImpl implements IRouterHandlerService {

	@Override
    public void onReceive(ImMsgBody imMsgBody) {
		
        //需要进行消息通知的userid
        Long userId = imMsgBody.getUserId();
        ChannelHandlerContext ctx = ChannelHandlerContextCache.get(userId);
        
        
        if (ctx != null) {
            ImMsg respMsg = ImMsg.build(ImMsgCodeEnum.IM_BIZ_MSG.getCode(), JSON.toJSONString(imMsgBody));
            ctx.writeAndFlush(respMsg);
        }
    }
	
	
}
