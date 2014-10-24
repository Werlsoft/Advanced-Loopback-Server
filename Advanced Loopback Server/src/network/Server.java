package network;

import java.io.IOException;
import java.net.ServerSocket;
import util.Handler;


public class Server implements Runnable {
	
	final ServerSocket server;
	Thread thread = new Thread(this);
	final Handler handle;
	
	/**
	 * 
	 * @param port Port to setup the server on.
	 * @throws IOException Thrown when there is errors.
	 */
	public Server(int port) throws IOException{
		server = new ServerSocket(port);
		handle = new Handler(); //you never need to access this.
		thread.start(); 
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				handle.add(server.accept()); //this will just sit and accept clients.
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
