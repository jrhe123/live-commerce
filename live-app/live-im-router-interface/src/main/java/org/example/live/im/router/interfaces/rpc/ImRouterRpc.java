package org.example.live.im.router.interfaces.rpc;

import org.example.live.im.dto.ImMsgBody;

public interface ImRouterRpc {

    /**
     * 发送消息
     *
     * @param imMsgBody
     * @return
     */
    boolean sendMsg(ImMsgBody imMsgBody);
}
