package com.example.demo;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class BioServer {

	public static void main(String[] args) throws IOException {
		
		ServerSocket serverSocket = new ServerSocket();
		// listen on port 9090
		serverSocket.bind(
				new InetSocketAddress(9090));
		// BIO: accept is blocked
		// accept client connections
		Socket socket = serverSocket.accept();
		
		while (true) {
			byte[] bytes = new byte[10];
			
			InputStream inputStream = socket.getInputStream(); 		
			// BIO: read is blocked
			inputStream.read(bytes);
			
			System.out.println("server recived data: " + new String(bytes));
			
		}
	}
}
