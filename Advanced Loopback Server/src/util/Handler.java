package util;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import network.Client;


public class Handler implements Runnable{

	List<Client> clients = Collections.synchronizedList(new ArrayList<Client>());
	Thread thread = new Thread(this);
	public static boolean nonloopback = true; //if this is false messages will be sent to the originator and all others
	public Handler(){
		thread.start();
	}
	
	public void add(Socket sock){
		try {
			synchronized(clients){
				clients.add(new Client(sock));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			synchronized (clients){
				Iterator<Client> I = clients.iterator();
				while(I.hasNext()){
					Client c = I.next();
					if(c.ready())
						this.send(c.read());
				}
			}
		}
	}
	
	public void send(String send){
		if(send != null)
			synchronized (clients){
				Iterator<Client> I = clients.iterator();
				while(I.hasNext()){
					Client c = I.next();
					if(!send.substring(0, 36).matches(c.id.toString()) && nonloopback) //Non-sendback feature!
						c.write(send.substring(36)); //Substring to trim UUID
					if(!nonloopback){
						c.write(send.substring(36));
					}
				}
			}
	}
	
}
