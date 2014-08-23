package Util;

public abstract class PacketReceiver<e> {
	private final Object lock = new Object();
	
	public final void notifyPacketReceived(e packet, ClientHolder<e> fromClient) {
	    synchronized(lock) {
	        packetReceived(packet, fromClient);
	    }
	}
	
	protected abstract void packetReceived(e packet, ClientHolder<e> fromClient);
}