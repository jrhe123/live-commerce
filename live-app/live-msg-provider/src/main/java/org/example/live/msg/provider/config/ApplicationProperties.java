package org.example.live.msg.provider.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "sms.twilio")
public class ApplicationProperties {

	private String sid;
    private String authToken;
    private String number;
    
    
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	
	@Override
	public String toString() {
		return "ApplicationProperties [sid=" + sid + ", authToken=" + authToken + ", number=" + number + "]";
	}
    
    
	
	
	
	
}
