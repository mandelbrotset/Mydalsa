package com.mydalsa.myflaxa.multiplayer.server;

import java.util.HashMap;

import tcp.server.TCPClientHolder;
import tcp.server.TCPServer;

public class Server {
	
	private TCPServer server;
	private HashMap<Integer, TCPClientHolder> clients;
	
	private void setupServer() {
		server = new TCPServer(new PackageReceiverImpl(), 8008, "bajs");
		server.startServer();
	}

}
