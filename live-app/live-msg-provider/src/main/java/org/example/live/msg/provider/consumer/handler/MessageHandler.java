package org.example.live.msg.provider.consumer.handler;

import org.example.live.im.dto.ImMsgBody;

public interface MessageHandler {

	/**
     * 
     * execute im server sent message
     * 处理im服务投递过来的消息内容
     *
     * @param imMsgBody
     */
    void onMsgReceive(ImMsgBody imMsgBody);
}
