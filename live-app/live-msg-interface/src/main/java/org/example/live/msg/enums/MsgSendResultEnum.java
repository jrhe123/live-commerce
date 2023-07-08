package org.example.live.msg.enums;

public enum MsgSendResultEnum {

	SEND_SUCCESS(0, "MSG_SUCCESS"),
	SEND_FAIL(1, "MSG_FAIL"),
	MSG_PARAM_ERROR(2, "MSG_PARAM_ERROR");
	
	int code;
	String desc;
	
	MsgSendResultEnum(int code, String desc){
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
