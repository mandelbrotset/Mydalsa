package com.mydalsa.myflaxa.multiplayer;

import com.mydalsa.myflaxa.multiplayer.client.Client;
import com.mydalsa.myflaxa.multiplayer.server.Server;

import tcp.client.TCPClient;
import tcp.server.TCPClientHolder;
import tcp.server.TCPServer;

public class MultiplayerHandler {

	
	private TCPServer server;
	private TCPClient client;
	private TCPClientHolder clientHolder;
	
	public MultiplayerHandler(boolean server) {
		if (server) {
			Server sh = new Server();
		} else {
			Client c = new Client();
		}
	}
	
	
	
	private void setupClient() {
		
	}
	
}
