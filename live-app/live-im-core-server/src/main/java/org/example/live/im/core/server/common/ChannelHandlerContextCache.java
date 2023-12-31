package org.example.live.im.core.server.common;

import java.util.HashMap;
import java.util.Map;

import io.netty.channel.ChannelHandlerContext;

public class ChannelHandlerContextCache {
	
	private static String SERVER_IP_ADDRESS = "";
	

	public static String getServerIpAddress() {
		return SERVER_IP_ADDRESS;
	}

	public static void setServerIpAddress(String sERVER_IP_ADDRESS) {
		SERVER_IP_ADDRESS = sERVER_IP_ADDRESS;
	}

	
	
	
	
	private static Map<Long, ChannelHandlerContext> channelHandlerContextMap =
			new HashMap<>();
	
	
	public static ChannelHandlerContext get(Long userId) {
		return channelHandlerContextMap.get(userId);
	}
	
	public static void put(Long userId, ChannelHandlerContext channelHandlerContext) {
		channelHandlerContextMap.put(userId, channelHandlerContext);
	}
	
	public static void remove(Long userId) {
		channelHandlerContextMap.remove(userId);
	}
}
