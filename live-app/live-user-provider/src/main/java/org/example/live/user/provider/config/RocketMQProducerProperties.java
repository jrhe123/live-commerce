package org.example.live.user.provider.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "rmq.producer")
@Configuration
public class RocketMQProducerProperties {
	
	// rocketmq name server addr
	private String nameSrv;
	// group name
	private String groupName;
	// message retry times
	private int retryTimes;
	// time out
	private int sendTimeOut;
	
	@Override
	public String toString() {
		return "RocketMQProducerProperties [nameSrv=" + nameSrv + ", groupName=" + groupName + ", retryTimes="
				+ retryTimes + ", sendTimeOut=" + sendTimeOut + "]";
	}
	
	
	public String getNameSrv() {
		return nameSrv;
	}
	public void setNameSrv(String nameSrv) {
		this.nameSrv = nameSrv;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getRetryTimes() {
		return retryTimes;
	}
	public void setRetryTimes(int retryTimes) {
		this.retryTimes = retryTimes;
	}
	public int getSendTimeOut() {
		return sendTimeOut;
	}
	public void setSendTimeOut(int sendTimeOut) {
		this.sendTimeOut = sendTimeOut;
	}
}
