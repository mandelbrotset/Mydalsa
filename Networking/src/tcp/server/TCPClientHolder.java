package tcp.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import util.PacketReceiver;
import util.server.ClientHolder;

public class TCPClientHolder extends ClientHolder<Object> {
	private static final int SLEEP = 1;
	protected String clientName;
	protected PacketReceiver<Object> objectReceiver;
	private Socket socket;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	
	public TCPClientHolder(Socket socket, PacketReceiver<Object> objectReceiver) throws IOException {
		this.socket = socket;
		this.objectReceiver = objectReceiver;
		createInputStream();
		createOutputStream();
	}
	
	public String getClientName() {
		return clientName;
	}
	
	public synchronized boolean write(Object object) throws IOException {
		outputStream.writeObject(object);
		return false;
	}
	
	private void createOutputStream() throws IOException {
		outputStream = new ObjectOutputStream(socket.getOutputStream());
	}
	
	private void createInputStream() throws IOException {
		inputStream = new ObjectInputStream(socket.getInputStream());
	}

	@Override
	public void run() {
		while (!interrupted()) {
			try {
				Object o = inputStream.readObject();
				objectReceiver.notifyObjectReceived(o, this);
			} catch (ClassNotFoundException | IOException e1) {
				e1.printStackTrace();
			}
			try {
				sleep(SLEEP);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		super.run();
	}
	
}
