package com.example.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class NioServer {
	
	private static List<SocketChannel> acceptSocketList = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		// bind to 9090, and config non-blocking
		serverSocketChannel.socket().bind(new InetSocketAddress(9090));
		serverSocketChannel.configureBlocking(false);
		
		
		// keep reading data, even it's empty "byteBuffer"
		// Notes: for example we have 10,000 socket in the list, it will cause the high CPU usage issue
		new Thread(() -> {
			while(true) {
				for (SocketChannel socketChannel : acceptSocketList) {
					try {
						
						ByteBuffer byteBuffer = ByteBuffer.allocate(10);
						socketChannel.read(byteBuffer);
						System.out.println("server recived data: " + 
								new String(byteBuffer.array()));
						
					} catch (IOException e) {
						e.printStackTrace();
					}					
				}
				
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		
		// async listening here
		while(true) {
			// accept - nio accept is non-blocking
			SocketChannel socketChannel = serverSocketChannel.accept();
			if (socketChannel != null) {
				System.out.println(">>>>> connection built");
				socketChannel.configureBlocking(false);
				acceptSocketList.add(socketChannel);
			}
		}
	}
}
