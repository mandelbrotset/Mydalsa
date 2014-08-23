package Util;

import java.io.IOException;

public abstract class ClientHolder<e> extends Thread {
	protected String clientName;
	protected PacketReceiver<e> packetReceiver;
	
	public String getClientName() {
		return clientName;
	}
	
	protected abstract void receivePacket() throws IOException, ClassNotFoundException;
	protected abstract boolean write(e packet) throws IOException;
}
