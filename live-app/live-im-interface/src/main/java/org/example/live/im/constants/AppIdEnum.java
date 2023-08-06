package org.example.live.im.constants;

public enum AppIdEnum {

	LIVE_BIZ_APP(10001, "live stream app");
	
	int code;
	String desc;
	
	AppIdEnum(int code, String desc) {
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
