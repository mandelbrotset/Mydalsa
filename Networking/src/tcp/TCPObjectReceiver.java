package tcp;

import util.PacketReceiver;

/**
 * This class should be extended, instantiated and sent into the TCPServer constructor.
 * When the server receives objects from a client, the method packetReceived will be called.
 *
 */
public abstract class TCPObjectReceiver extends PacketReceiver<Object> {
	
	
}
