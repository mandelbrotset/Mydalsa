package com.mydalsa.myflaxa.multiplayer.server;

import java.util.HashMap;
import tcp.server.TCPServer;

public class Server {
	
	private TCPServer server;
	private HashMap<Integer, ConnectedClient> clients;
	
	private void setupServer(int port, String name) {
		server = new TCPServer(new PackageReceiverImpl(this), 8008, name);
		server.startServer();
	}
	
	public HashMap<Integer, ConnectedClient> getClients() {
		return clients;
	}
	
	public void addClient(ConnectedClient cc) {
		clients.put(cc.getID(), cc);
	}

}
