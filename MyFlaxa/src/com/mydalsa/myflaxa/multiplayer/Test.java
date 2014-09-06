package com.mydalsa.myflaxa.multiplayer;

import tcp.server.TCPServer;

public class Test {
	TCPServer tcpServer;
	PackageReceiverImpl packageReceiverImpl;
	
	public Test() {
		packageReceiverImpl = new PackageReceiverImpl();
		tcpServer = new TCPServer(packageReceiverImpl, 8008, "yey");
		tcpServer.startServer();
	}
}
