package Util;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Server<E> extends Thread {
	
	protected PacketReceiver<E> packetReceiver;
	protected PacketSender packetSender;
	protected ServerListHandler serverListHandler;
	protected Terminal terminal;
	protected int port;
	protected ArrayList<ClientHolder<E>> clients;
	
	public Server(PacketReceiver<E> packetReceiver, int port) {
		this.packetReceiver = packetReceiver;
		terminal = new Terminal();
		this.port = port;
		clients = new ArrayList<ClientHolder<E>>();
	}
	
	public abstract boolean startServer() throws IOException;
	
	public abstract boolean stopServer() throws IOException;
	
}
