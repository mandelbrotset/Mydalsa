package com.mydalsa.myflaxa.multiplayer.server;

import java.io.IOException;
import java.net.Socket;

import com.mydalsa.myflaxa.multiplayer.MultiplayerSpriteList;

import tcp.TCPObjectReceiver;
import tcp.server.TCPClientHolder;

public class ConnectedClient {

	private TCPClientHolder clientHolder;
	
	public ConnectedClient(TCPClientHolder clientHolder) {
		this.clientHolder = clientHolder;
		list = new MultiplayerSpriteList();
	}

	private MultiplayerSpriteList list;

	public MultiplayerSpriteList getList() {
		synchronized (list) {
			return list;
		}
	}
	
	public int getID() {
		return clientHolder.getID();
	}

	public void setList(MultiplayerSpriteList list) {
		synchronized (list) {
			this.list = list;
		}
	}
}
