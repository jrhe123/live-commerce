package org.example.live.user.provider.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "rmq.consumer")
@Configuration
public class RocketMQConsumerProperties {

	// rocketmq name server addr
	private String nameSrv;
	// group name
	private String groupName;
	
	
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
	
	@Override
	public String toString() {
		return "RocketMQConsumerProperties [nameSrv=" + nameSrv + ", groupName=" + groupName + "]";
	}
	
	
}
