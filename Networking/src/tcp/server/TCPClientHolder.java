package tcp.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import tcp.TCPObjectReceiver;
import util.PacketReceiver;
import util.server.ClientHolder;

public class TCPClientHolder extends ClientHolder<Object> {
	private static final int SLEEP = 1;
	protected String clientName;
	protected PacketReceiver<Object> objectReceiver;
	private Socket socket;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	private int id;
	
	public TCPClientHolder(Socket socket, TCPObjectReceiver objectReceiver) throws IOException {
		id = general.IDGenerator.getNewID();
		this.socket = socket;
		this.objectReceiver = objectReceiver;
		createInputStream();
		createOutputStream();
	}
	
	public int getID() {
		return id;
	}
	
	public String getClientName() {
		return clientName;
	}
	
	public InetAddress getIP() {
		return socket.getInetAddress();
	}
	
	public int getPort() {
		return socket.getPort();
	}
	
	@Override
	public String toString() {
		return getIP() + ":" + getPort();
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
				Object o;
				try {
					o = inputStream.readObject();
					objectReceiver.notifyObjectReceived(o, this);

				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
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
