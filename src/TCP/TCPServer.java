package TCP;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import TCP.TCPClientHolder;
import TCP.TCPObjectReceiver;
import Util.Server;
import Util.ServerListHandler;
import Util.Terminal;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class TCPServer extends Server {
	private final static int SLEEP = 1;
	private TCPObjectReceiver objectReceiver;
	private ServerListHandler serverListHandler;
	private Terminal terminal;
	private int port;
	private ArrayList<TCPClientHolder> clients;
	private ServerSocket socket;
	
	public TCPServer(TCPObjectReceiver objectReceiver, int port) {
		this.objectReceiver = objectReceiver;
		terminal = new Terminal();
		this.port = port;
		clients = new ArrayList<TCPClientHolder>();
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
		return false;
	}

	@Override
	public void run() {
		while (!interrupted()) {
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
		super.run();
	}
	
	public void startRealtimeServer() {
		throw new NotImplementedException();
	}
	
}
