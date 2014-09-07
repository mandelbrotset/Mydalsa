package com.mydalsa.myflaxa.multiplayer;

import tcp.client.TCPClient;
import tcp.server.TCPClientHolder;
import tcp.server.TCPServer;

public class MultiplayerHandler {

	
	private TCPServer server;
	private TCPClient client;
	private TCPClientHolder clientHolder;
	
	public MultiplayerHandler(boolean server) {
		if (server) {
			ServerHandler sh = new ServerHandler();
		} else {
			ClientHandler ch = new ClientHandler(null,0);
		}
	}
	
	
	
	private void setupClient() {
		
	}
	
}
