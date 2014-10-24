package network;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;


public class Client {

	final PrintWriter pw;
	final BufferedReader br;
	/**
	 * Used to identify the client's unique id.
	 */
	public final UUID id = UUID.randomUUID();
	
	/**
	 * 
	 * @param socket Socket of remote client.
	 * @throws IOException Thrown if the socket is not connected properly.
	 */
	public Client(Socket socket) throws IOException{
		pw = new PrintWriter(socket.getOutputStream(), true);
		br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	/**
	 * 
	 * @return Message 
	 * 
	 * <p>Returns the a string containing the client's UUID and the message.</p>
	 */
	public String read(){
		try {
			return id + br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @return true when the buffer contains anything.
	 */
	public boolean ready(){
		try {
			return br.ready();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 
	 * @param msg message to write.
	 */
	public void write(String msg){
		pw.println(msg);
	}
}
