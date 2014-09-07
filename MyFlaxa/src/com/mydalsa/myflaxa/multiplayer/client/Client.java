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
		synchronized (list) {
			return list;
		}
	}
	
	public void setList(MultiplayerSpriteList list) {
		synchronized (list) {
			this.list = list; // om allt låser sig, tänk här
		}
	}

	@Override
	protected void packetReceived(Object packet) {
		if(packet instanceof MultiplayerSpriteList){
			synchronized (list) {
				list = (MultiplayerSpriteList) packet; // och här. Hur blir det när man assignar i en låsning??
			}
		}
	}
}
