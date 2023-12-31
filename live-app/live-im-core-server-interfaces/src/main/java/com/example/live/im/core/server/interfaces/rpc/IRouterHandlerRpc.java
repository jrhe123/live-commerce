package com.example.live.im.core.server.interfaces.rpc;

import org.example.live.im.dto.ImMsgBody;

public interface IRouterHandlerRpc {

    /**
     * 专门给router服务
     * use userId to send back IM
     * 按照用户id进行消息的发送
     *
     * @param imMsgBody
     */
    void sendMsg(ImMsgBody imMsgBody);
}
