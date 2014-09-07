package com.mydalsa.myflaxa.multiplayer.server;

import Logging.Logger;
import tcp.TCPObjectReceiver;
import util.server.ClientHolder;

public class PackageReceiverImpl extends TCPObjectReceiver {

	@Override
	protected void packetReceived(Object packet, ClientHolder<Object> fromClient) {
		
		Logger.getInstance().write("package received");
		
	}
	
	
	
}
