package org.example.live.id.generate.enums;

public enum IdTypeEnum {

	USER_ID(1, "user id generate strategy");
	
	int code;
	String desc;
	
	IdTypeEnum(int code, String desc){
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
