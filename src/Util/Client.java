package Util;
import java.io.IOException;
import java.net.InetAddress;


public abstract class Client extends Thread {
	protected InetAddress ip;
	protected int port;
	public abstract boolean connectToServer(InetAddress ip, int port) throws IOException;
	
	public Client(InetAddress ip, int port) {
		this.ip = ip;
		this.port = port;
		
	}
}
