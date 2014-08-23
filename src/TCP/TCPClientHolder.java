package TCP;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

import Util.ClientHolder;

class TCPClientHolder extends ClientHolder<Object> {

	private Socket socket;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	
	
	public TCPClientHolder(Socket socket, TCPPacketReceiver tcpPacketReceiver) throws IOException {
		this.socket = socket;
		this.packetReceiver = tcpPacketReceiver;
		createInputStream();
		createOutputStream();
	}
	
	public Socket getSocket() {
		return socket;
	}

	@Override
	public void run() {
		while (!interrupted()) {
			try {
				receivePacket();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		super.run();
	}

	@Override
	protected void receivePacket() throws ClassNotFoundException, IOException {
		Object receivedObjcet = inputStream.readObject();
		
		packetReceiver.notifyPacketReceived(receivedObjcet, this);
	}
	
	@Override
	protected boolean write(Object packet) throws IOException {
		outputStream.writeObject(packet);
		return false;
	}

	private void createOutputStream() throws IOException {
		outputStream = new ObjectOutputStream(socket.getOutputStream());
	}
	
	private void createInputStream() throws IOException {
		inputStream = new ObjectInputStream(socket.getInputStream());
	}
	
}
