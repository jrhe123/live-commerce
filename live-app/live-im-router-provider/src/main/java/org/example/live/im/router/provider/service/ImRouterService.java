package org.example.live.im.router.provider.service;

import org.example.live.im.dto.ImMsgBody;

public interface ImRouterService {

    /**
     * 发送消息
     *
     * @param imMsgBody
     * @return
     */
    boolean sendMsg(ImMsgBody imMsgBody);
}
