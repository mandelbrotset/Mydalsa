package Util;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Server<e> extends Thread {
	
	protected PacketReceiver<e> packetReceiver;
	protected PacketSender packetSender;
	protected ServerListHandler serverListHandler;
	protected Terminal terminal;
	protected int port;
	protected ArrayList<ClientHolder<e>> clients;
	
	public Server(PacketReceiver<e> packetReceiver, int port) {
		this.packetReceiver = packetReceiver;
		terminal = new Terminal();
		this.port = port;
		clients = new ArrayList<ClientHolder<e>>();
	}
	
	public abstract boolean startServer() throws IOException;
	
	public abstract boolean stopServer() throws IOException;
	
	@Override
	public void run() {
		while (!interrupted()) {
			
		}
		super.run();
	}
	
	private void checkConnections() {
		
	}
	
}
