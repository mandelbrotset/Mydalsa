package com.mydalsa.myflaxa.multiplayer;

import java.net.InetAddress;

import tcp.client.TCPClient;

public class ClientHandler {

	TCPClient client;
	
	public ClientHandler(InetAddress ip, int port) {
		client = new TCPClient(ip, port) {
			
			public int hej;
			@Override
			protected void packetReceived(Object packet) {
				
				
			}
		};
	}
}
