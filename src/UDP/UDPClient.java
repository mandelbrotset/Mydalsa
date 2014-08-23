package UDP;
import Util.Client;

import java.net.InetAddress;


public class UDPClient extends Client {

	public UDPClient(InetAddress ip, int port) {
		super(ip, port);
	}

	@Override
	public boolean connectToServer(InetAddress ip, int port) {
		return false;
	}

}
