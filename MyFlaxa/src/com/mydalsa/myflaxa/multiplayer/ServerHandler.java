package com.mydalsa.myflaxa.multiplayer;

import java.util.HashMap;

import tcp.server.TCPServer;

public class ServerHandler {
	
	private TCPServer server;
	private HashMap<Integer, ConnectedClient> clients;
	
	private void setupServer() {
		server = new TCPServer(new PackageReceiverImpl(), 8008, "bajs");
		server.startServer();
	}

}
