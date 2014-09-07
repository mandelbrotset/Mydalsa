package util.client;
import java.io.IOException;
import java.net.InetAddress;

public abstract class Client<e> extends Thread {
	private final Object lock = new Object();
	public abstract boolean connectToServer(InetAddress ip, int port) throws IOException;
	
	
	public final void notifyPacketReceived(e packet) {
	    synchronized(lock) {
	        packetReceived(packet);
	    }
	}
	
	protected abstract void write(e packet) throws IOException;
	protected abstract void packetReceived(e packet);
}
