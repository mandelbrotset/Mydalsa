package com.mydalsa.myflaxa.multiplayer.server;

import Logging.Logger;
import tcp.TCPObjectReceiver;
import tcp.server.TCPClientHolder;
import util.server.ClientHolder;

public class PackageReceiverImpl extends TCPObjectReceiver {

	@Override
	protected void packetReceived(Object packet, ClientHolder<Object> fromClient) {
		TCPClientHolder tch = (TCPClientHolder) fromClient;
		
		Logger.getInstance().write("package received");
		
	}
	
}
