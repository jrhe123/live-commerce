package org.example.live.common.interfaces.enums;

public enum CommonStatusEnum {

	INVALID_STATUS(0, "INVALID"),
	VALID_STATUS(1, "VALID");
	
	CommonStatusEnum(int code, String desc){
		this.code = code;
		this.desc = desc;
	}
	
	int code;
	String desc;
	
	
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