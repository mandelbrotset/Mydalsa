package tcp.client;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import util.client.Client;

public abstract class TCPClient extends Client<Object> {
	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	
	public TCPClient(InetAddress ip, int port) {
		super(ip, port);
	}

	@Override
	public boolean connectToServer(InetAddress ip, int port) throws IOException {
		socket = new Socket(ip, port);
		start();
		return false;
	}

	@Override
	public void write(Object packet) throws IOException {
		outputStream.writeObject(packet);
	}
	
	private void createInputStream() throws IOException {
		inputStream = new ObjectInputStream(socket.getInputStream());
	}
	
	private void createOutputStream() throws IOException {
		outputStream = new ObjectOutputStream(socket.getOutputStream());
	}
	
	private boolean pingServer() {
		// one byte to the PingResponder and wait for reply
		throw new NotImplementedException();
	}
	
	@Override
	protected abstract void packetReceived(Object packet);

}
