package TCP;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import Util.Client;


public class TCPClient extends Client {
	private Socket socket;
	
	public TCPClient(InetAddress ip, int port) {
		super(ip, port);
	}

	@Override
	public boolean connectToServer(InetAddress ip, int port) throws IOException {
		socket = new Socket(ip, port);
		start();
		return false;
	}

}
