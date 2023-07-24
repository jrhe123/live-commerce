package org.example.live.im.core.server.service;

import org.example.live.im.dto.ImMsgBody;

public interface IRouterHandlerService {

    /**
     * 当收到业务服务的请求，进行处理
     * 
     * check rpc, if the context is matched, then call the im server
     *
     * @param imMsgBody
     */
    void onReceive(ImMsgBody imMsgBody);
}
