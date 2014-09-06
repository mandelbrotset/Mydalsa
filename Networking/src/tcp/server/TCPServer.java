package tcp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;

import Logging.LogLevel;
import Logging.Logger;
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
	private String serverName;
	
	public TCPServer(TCPObjectReceiver objectReceiver, int port, String serverName) {
		this.serverName = serverName;
		setName("TCPServer_" + serverName);
		isRunning = false;
		this.objectReceiver = objectReceiver;
		this.port = port;
		terminal = new Terminal();
		clients = new ArrayList<TCPClientHolder>();
	}
	
	public boolean startServer() {
		try {
			socket = new ServerSocket(port);
			this.start();
			logInfo("Server started");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean stopServer() {
		isRunning = false;
		try {
			socket.close();
			this.join();
			logInfo("Server stopped");
			return true;
		} catch (InterruptedException e1) {
			logError("Error when stopping server: " + e1.getMessage());
			e1.printStackTrace();
		}catch (IOException e2) {
			logError("Error when stopping server: " + e2.getMessage());
			e2.printStackTrace();
		}
		return false;
	}
	
	@Override
	public void run() {
		isRunning = true;
		
		while (!interrupted() && isRunning) {
			try {
				java.net.Socket clientSocket = socket.accept();
				logInfo("new client connected");
				TCPClientHolder ch = new TCPClientHolder(socket.accept(), objectReceiver);
				logInfo("clientholder created: " + ch.toString());
				synchronized (clients) {
					clients.add(ch);
				}
			} catch (SocketException se) {
				logInfo("Socket closed");
			} catch (IOException e1) {
				logError(e1.getMessage());
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
	
	private void logInfo(String message) {
		Logger.getInstance().write(LogLevel.info, message, getName());
	}
	
	private void logError(String message) {
		Logger.getInstance().write(LogLevel.error, message, getName());
	}
}
