package bio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class BioClient {

//	public static void main(String[] args) throws IOException, InterruptedException {
//		// connect to BioServer
//		Socket socket = new Socket();
//		socket.connect(
//				new InetSocketAddress(9090)
//				);
//		
//		// send data
//		OutputStream outputStream = socket.getOutputStream();
//		while(true) {
//			outputStream.write("test".getBytes());
//			outputStream.flush();
//			System.out.println("Sending data to socket server");
//			
//			Thread.sleep(1000);
//		}
//	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
        AtomicInteger connectCount = new AtomicInteger(0);
        //连接bio server
        CountDownLatch count = new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Socket socket = new Socket();
                        count.await();
                        socket.connect(new InetSocketAddress(9090));
                        System.out.println("连接完成" + connectCount.getAndIncrement());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }
        count.countDown();
        Thread.sleep(100000);
    }
}
