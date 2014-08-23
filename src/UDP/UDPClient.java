package UDP;
import Util.Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class UDPClient extends Client<Message> {
	private DatagramSocket socket;
	
	public UDPClient(InetAddress ip, int port) {
		super(ip, port);
	}

	@Override
	public boolean connectToServer(InetAddress ip, int port) {
		return false;
	}
	
	@Override
	public void write(Message packet) throws IOException {
		socket.send(packet.getDatagramPacket());
	}

	@Override
	protected void packetReceived(Message packet) {
		// TODO Auto-generated method stub
	}
	

}
