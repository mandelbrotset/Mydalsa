package tcp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import tcp.TCPObjectReceiver;
import util.Terminal;
import util.server.Server;
import util.server.ServerListHandler;

public class TCPServer extends Server {
	private final static int SLEEP = 1;
	private TCPObjectReceiver objectReceiver;
	private ServerListHandler serverListHandler;
	private Terminal terminal;
	private int port;
	private ArrayList<TCPClientHolder> clients;
	private ServerSocket socket;
	private boolean isRunning;
	
	public TCPServer(TCPObjectReceiver objectReceiver, int port) {
		this.objectReceiver = objectReceiver;
		terminal = new Terminal();
		this.port = port;
		clients = new ArrayList<TCPClientHolder>();
		isRunning = false;
	}
	
	public boolean startServer() {
		try {
			socket = new ServerSocket(port);
			this.start();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}		
	}
	
	public boolean stopServer() {
		isRunning = false;
		try {
			this.join();
			return true;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public void run() {
		isRunning = true;
		
		while (!interrupted() && isRunning) {
			try {
				TCPClientHolder ch = new TCPClientHolder(socket.accept(), objectReceiver);
				synchronized (clients) {
					clients.add(ch);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				sleep(SLEEP);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void startRealtimeServer() {
		throw new NotImplementedException();
	}
	
}
