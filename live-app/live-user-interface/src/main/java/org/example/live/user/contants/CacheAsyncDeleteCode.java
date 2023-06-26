package org.example.live.user.contants;

public enum CacheAsyncDeleteCode {
	
	USER_INFO_DELETE(0, "user info delete"),
	USER_TAG_DELETE(1, "user tag info delete");
	
	int code;
	String desc;
	
	CacheAsyncDeleteCode(int code, String desc){
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
