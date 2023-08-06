package org.example.live.msg.provider.consumer.handler.impl;

import org.apache.dubbo.config.annotation.DubboReference;
import org.example.live.im.constants.AppIdEnum;
import org.example.live.im.dto.ImMsgBody;
import org.example.live.im.router.interfaces.rpc.ImRouterRpc;
import org.example.live.msg.dto.MessageDTO;
import org.example.live.msg.enums.ImMsgBizCodeEnum;
import org.example.live.msg.provider.consumer.handler.MessageHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


@Component
public class MessageHandlerImpl implements MessageHandler {
	
	@DubboReference
    private ImRouterRpc routerRpc;
	

	@Override
	public void onMsgReceive(ImMsgBody imMsgBody) {
				
		int bizCode = imMsgBody.getBizCode();
		
		// live stream message: 直播间的聊天消息
        if (ImMsgBizCodeEnum.LIVING_ROOM_IM_CHAT_MSG_BIZ.getCode() == bizCode) {
            MessageDTO messageDTO = JSON.parseObject(imMsgBody.getData(), MessageDTO.class);
            
            //
            ImMsgBody respMsg = new ImMsgBody();
            respMsg.setUserId(messageDTO.getObjectId());
            respMsg.setAppId(AppIdEnum.LIVE_BIZ_APP.getCode());
            respMsg.setBizCode(ImMsgBizCodeEnum.LIVING_ROOM_IM_CHAT_MSG_BIZ.getCode());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("senderId", messageDTO.getUserId());
            jsonObject.put("content", messageDTO.getContent());
            respMsg.setData(jsonObject.toJSONString());
            
            
            
            routerRpc.sendMsg(respMsg);
        }
	}

}
