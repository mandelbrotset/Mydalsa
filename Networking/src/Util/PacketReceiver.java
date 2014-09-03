package util;

import util.server.ClientHolder;

public abstract class PacketReceiver<E> {
	private final Object lock = new Object();
	
	public final void notifyObjectReceived(E packet, ClientHolder<E> fromClient) {
	    synchronized(lock) {
	        packetReceived(packet, fromClient);
	    }
	}
	
	protected abstract void packetReceived(E packet, ClientHolder<E> fromClient);
}