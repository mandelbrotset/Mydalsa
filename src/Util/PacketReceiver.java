package Util;

public abstract class PacketReceiver<e> {
	public abstract void packetReceived(e packet, ClientHolder<e> fromClient);
}