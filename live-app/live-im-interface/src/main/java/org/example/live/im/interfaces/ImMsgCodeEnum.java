package org.example.live.im.interfaces;

public enum ImMsgCodeEnum {

	IM_LOGIN_MSG(1001, "login im message"),
	IM_LOGOUT_MSG(1002, "logout im message"),
	IM_BIZ_MSG(1003, "common business im message"),
	IM_HEARTBEAT_MSG(1004, "heart beat im message");
	
	private int code;
	private String desc;
	
	
	ImMsgCodeEnum(int code, String desc) {
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
