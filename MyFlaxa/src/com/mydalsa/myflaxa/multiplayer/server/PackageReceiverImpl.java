package com.mydalsa.myflaxa.multiplayer.server;

import com.mydalsa.myflaxa.multiplayer.MultiplayerSprite;
import com.mydalsa.myflaxa.multiplayer.MultiplayerSpriteList;

import Logging.LogLevel;
import Logging.Logger;
import tcp.TCPObjectReceiver;
import tcp.server.TCPClientHolder;
import util.server.ClientHolder;

public class PackageReceiverImpl extends TCPObjectReceiver {

	Server server;
	
	public PackageReceiverImpl(Server server) {
		this.server = server;
	}
	
	@Override
	protected void packetReceived(Object packet, ClientHolder<Object> fromClient) {
		TCPClientHolder tch = (TCPClientHolder) fromClient;
		if (packet instanceof MultiplayerSprite) {
			
			Logger.getInstance().write("sprite received");
		} else if (packet instanceof MultiplayerSpriteList) {
			Logger.getInstance().write("spritelist received");
		}
		Logger.getInstance().write("package received");
		
	}

	@Override
	public void clientConnected(TCPClientHolder client) {
		if (server.getClients().containsKey(client.getID())) {
			Logger.getInstance().write(LogLevel.warning, "client got a non-unique ID: " + client.getID());
		} else {
			Logger.getInstance().write("client connected: " + client.toString());
			ConnectedClient connectedClient = new ConnectedClient(client);
			server.getClients().put(connectedClient.getID(), connectedClient);
		}
	}
	
}
