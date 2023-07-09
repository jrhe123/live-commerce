package org.example.live.msg.provider.utils;

import org.example.live.msg.provider.config.ApplicationProperties;
import org.springframework.stereotype.Component;

import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import jakarta.annotation.Resource;

@Component
public class SMSUtils {

	@Resource
	private ApplicationProperties applicationProperties;
	
	@SuppressWarnings("finally")
	public boolean sendSMS(String phone, String code) {
		try {
			Twilio.init(
					applicationProperties.getSid(), applicationProperties.getAuthToken());
	        Message.creator(
	        		new PhoneNumber(phone),
	                new PhoneNumber(applicationProperties.getNumber()), 
	                "[Live Stream] Your verification code: " + code
	                + ". Please enter your code within 1 min."
	                ).create();
	        return true;
		} catch (ApiException e) {
			throw new RuntimeException(e);
		} finally {
			return true;
		}
	}
}
