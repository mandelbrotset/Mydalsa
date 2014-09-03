package UDP;

import Util.Client;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract class UDPClient extends Client<Message> {
	private DatagramSocket socket;

	public UDPClient(InetAddress ip, int port) {
		super(ip, port);
	}

	@Override
	public boolean connectToServer(InetAddress ip, int port) {
		throw new NotImplementedException();
	}

	@Override
	public void write(Message packet) throws IOException {
		socket.send(packet.getDatagramPacket());
	}

}
