package tcp;

import tcp.server.TCPClientHolder;
import util.PacketReceiver;
import util.server.ClientHolder;

/**
 * This class should be extended, instantiated and sent into the TCPServer constructor.
 * When the server receives objects from a client, the method packetReceived will be called.
 *
 */
public abstract class TCPObjectReceiver extends PacketReceiver<Object> {
	private final Object lock = new Object();
	
	public abstract void clientConnected(TCPClientHolder client);
	
	public final void notifyClientConnected(TCPClientHolder client) {
	    synchronized(lock) {
	        clientConnected(client);
	    }
	}

}
