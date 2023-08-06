package org.example.live.msg.enums;

public enum ImMsgBizCodeEnum {

	LIVING_ROOM_IM_CHAT_MSG_BIZ(5555, "live stream room chat room message");
	
	int code;
	String desc;
	
	ImMsgBizCodeEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	
	public int getCode() {
		return code;
	}
	
	
	public String getDesc() {
		return desc;
	}
	
	
}
