package UDP;
import java.net.*;
import Util.PacketReceiver;
import Util.Server;

public class UDPServer extends Server {

	private DatagramSocket socket;
	
	public UDPServer(PacketReceiver packetReceiver, int port) {
		super(packetReceiver, port);
		setName(this.getClass().getName());
	}

	@Override
	public boolean startServer() {
		start();
		return false;
	}

	@Override
	public boolean stopServer() {
		socket.close();
		return false;
	}

}
