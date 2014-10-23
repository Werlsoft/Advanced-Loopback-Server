package app;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import util.Handler; //If line 22 (Handler.nonloopback = false;) is commented this will give a warning this is normal.
import network.Client;
import network.Server;


public class start implements Runnable{

	Thread thread = new Thread(this);
	
	public start(){
		
		//Handler.nonloopback = false;	//Uncomment to send copy of message to sender.
		
		thread.start();
	}
	
	public static void main(String[] args){
		try {
			new Server(3412);
			new start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Client c = new Client(new Socket("localhost",3412));
			Thread.sleep(500); //This is to ensure the Non-Sendback test completes first
			c.write("- - - -  Server Startup  - - - -\n");
			c.write("- - - -  IPv4 Addresses  - - - -");
			List<InetAddress> addrList = new ArrayList<InetAddress>();
			Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
			while(e.hasMoreElements()) {
				NetworkInterface ifc = e.nextElement();
			   if(ifc.isUp()) {
				   Enumeration<InetAddress> a = ifc.getInetAddresses();
			      while(a.hasMoreElements()) {
			        addrList.add(a.nextElement());
			      }
			   }
			}
			for(InetAddress ip : addrList)
				if(!ip.isLoopbackAddress() && checkIPv4(ip.getHostAddress()))
					c.write(ip.getHostAddress());
			c.write("-- -- -- -- -- -- -- -- -- -- --\n\n- - - -  IPv6 Addresses  - - - -");
			for(InetAddress ip : addrList)
				if(!ip.isLoopbackAddress() && !checkIPv4(ip.getHostAddress()))
					c.write(ip.getHostAddress());
			c.write("-- -- -- -- -- -- -- -- -- -- --\n");
			c.write("- - - - -  Self Tests  - - - - -\n");
			c.write("Testing Loopback Connectivity");
			InetAddress local = InetAddress.getLoopbackAddress();
			c.write(local.isReachable(5000) ? "Sucsess" : "Failure");
			c.write("Testing Internet Connectivity");
			c.write("This may fail if not run as an Admin/Root do not be alarmed!");
			InetAddress ip = InetAddress.getByAddress(new byte[] {8,8,8,8});
			c.write(ip.isReachable(5000) ? "Sucsess" : "Failure");
			c.write("- - - - - Server Ready - - - - -\n");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	public static final boolean checkIPv4(final String ip) {
	    boolean isIPv4;
	    try {
	    final InetAddress inet = InetAddress.getByName(ip);
	    isIPv4 = inet.getHostAddress().equals(ip)
	            && inet instanceof Inet4Address;
	    } catch (final UnknownHostException e) {
	    isIPv4 = false;
	    }
	    return isIPv4;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Client c = new Client(new Socket("localhost",3412));
			c.write("Non-Sendback test FAILURE");
			c.write("Clients will be sent their own messages!");
			while(true){
				if(c.ready())
					System.out.println(c.read().substring(36));
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
