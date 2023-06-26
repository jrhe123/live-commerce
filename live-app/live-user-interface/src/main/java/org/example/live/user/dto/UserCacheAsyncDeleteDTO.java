package org.example.live.user.dto;

import java.io.Serial;
import java.io.Serializable;

public class UserCacheAsyncDeleteDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = -6604464874552994896L;
	
	/**
	 * different code, to do different logic
	 */
	private int code;
	private String json;
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	
	
	@Override
	public String toString() {
		return "UserCacheAsyncDeleteDTO [code=" + code + ", json=" + json + "]";
	}
}
