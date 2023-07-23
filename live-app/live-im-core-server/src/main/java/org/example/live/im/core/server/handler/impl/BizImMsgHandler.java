package org.example.live.im.core.server.handler.impl;

import org.example.live.im.core.server.common.ImContextUtils;
import org.example.live.im.core.server.common.ImMsg;
import org.example.live.im.core.server.handler.SimplyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandlerContext;

@Component
public class BizImMsgHandler implements SimplyHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(BizImMsgHandler.class);

//    @Resource
//    private MQProducer mqProducer;

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
        
        
        byte[] body = imMsg.getBody();
        if (body == null || body.length == 0) {
            LOGGER.error("body error,imMsg is {}", imMsg);
            return;
        }
        
        
//        Message message = new Message();
//        message.setTopic(ImCoreServerProviderTopicNames.QIYU_LIVE_IM_BIZ_MSG_TOPIC);
//        message.setBody(body);
        
        
        try {
//            SendResult sendResult = mqProducer.send(message);
//            LOGGER.info("[BizImMsgHandler]消息投递结果:{}", sendResult);
        } catch (Exception e) {
            LOGGER.error("send error ,erros is :", e);
            throw new RuntimeException(e);
        }
    }

}
