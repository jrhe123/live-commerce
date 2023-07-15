package com.example.demo;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class BioClient {

	public static void main(String[] args) throws IOException, InterruptedException {
		// connect to BioServer
		Socket socket = new Socket();
		socket.connect(
				new InetSocketAddress(9090)
				);
		
		// send data
		OutputStream outputStream = socket.getOutputStream();
		while(true) {
			outputStream.write("test".getBytes());
			outputStream.flush();
			System.out.println("Sending data to socket server");
			
			Thread.sleep(1000);
		}
	}
}
