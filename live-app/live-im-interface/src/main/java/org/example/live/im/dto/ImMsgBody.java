package org.example.live.im.dto;

import java.io.Serial;
import java.io.Serializable;

public class ImMsgBody implements Serializable {

	@Serial
	private static final long serialVersionUID = -729589590629580691L;

	private int appId;
	
	private long userId;
	
	private String token;
	
	private String data;

	
	


	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	
	
	@Override
	public String toString() {
		return "ImMsgBody [appId=" + appId + ", userId=" + userId + ", token=" + token + ", data=" + data + "]";
	}
	
}
