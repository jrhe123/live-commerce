package org.example.live.msg.provider.consumer.handler.impl;

import org.apache.dubbo.config.annotation.DubboReference;
import org.example.live.im.dto.ImMsgBody;
import org.example.live.msg.provider.consumer.handler.MessageHandler;
import org.springframework.stereotype.Component;


@Component
public class MessageHandlerImpl implements MessageHandler {
	
	@DubboReference
    // private ImRouterRpc routerRpc;
	

	@Override
	public void onMsgReceive(ImMsgBody imMsgBody) {
//		int bizCode = imMsgBody.getBizCode();
		
		//直播间的聊天消息
//        if (ImMsgBizCodeEnum.LIVING_ROOM_IM_CHAT_MSG_BIZ.getCode() == bizCode) {
//            MessageDTO messageDTO = JSON.parseObject(imMsgBody.getData(), MessageDTO.class);
//            //暂时不做过多的处理
//            ImMsgBody respMsg = new ImMsgBody();
//            respMsg.setUserId(messageDTO.getObjectId());
//            respMsg.setAppId(AppIdEnum.QIYU_LIVE_BIZ.getCode());
//            respMsg.setBizCode(ImMsgBizCodeEnum.LIVING_ROOM_IM_CHAT_MSG_BIZ.getCode());
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("senderId", messageDTO.getUserId());
//            jsonObject.put("content", messageDTO.getContent());
//            respMsg.setData(jsonObject.toJSONString());
//            routerRpc.sendMsg(respMsg);
//        }
	}

}
