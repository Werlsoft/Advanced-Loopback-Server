package network;

import java.io.IOException;
import java.net.ServerSocket;
import util.Handler;


public class Server implements Runnable {
	
	final ServerSocket server;
	Thread thread = new Thread(this);
	final Handler handle;
	
	public Server(int port) throws IOException{
		server = new ServerSocket(port);
		handle = new Handler();
		thread.start(); 
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				handle.add(server.accept());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
