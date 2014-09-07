package com.mydalsa.myflaxa.multiplayer.client;

import com.mydalsa.myflaxa.multiplayer.MultiplayerSpriteList;

import tcp.client.TCPClient;

public class Client extends TCPClient {

	TCPClient client;
	MultiplayerSpriteList list;
	
	public Client() {
		list = new MultiplayerSpriteList();
	}
	
	public MultiplayerSpriteList getList() {
		return list;
	}
	public void setList(MultiplayerSpriteList list) {
		this.list = list;
	}

	@Override
	protected void packetReceived(Object packet) {
		if(packet instanceof MultiplayerSpriteList){
			list = (MultiplayerSpriteList) packet;
		}
	}
}
