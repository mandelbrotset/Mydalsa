package UDP;

import java.net.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import Util.PacketReceiver;
import Util.Server;

public class UDPServer extends Server {

	private DatagramSocket socket;

	public UDPServer(PacketReceiver<Message> packetReceiver, int port) {
		setName(this.getClass().getName());
	}

	@Override
	public boolean startServer() {
		start();
		throw new NotImplementedException();
	}

	@Override
	public boolean stopServer() {
		socket.close();
		return false;
	}

}
