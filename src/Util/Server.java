package Util;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server<e> extends Thread {
	
	protected PacketReceiver<e> packetReceiver;
	protected PacketSender packetSender;
	protected ServerListHandler serverListHandler;
	protected Terminal terminal;
	protected int port;
	protected ArrayList<ClientHolder<e>> clients;
	private DatagramSocket pingSocket;
	private ServerSocket tcpSocket;
	
	public Server(PacketReceiver<e> packetReceiver, int port) {
		this.packetReceiver = packetReceiver;
		terminal = new Terminal();
		this.port = port;
		clients = new ArrayList<ClientHolder<e>>();
	}
	
	public abstract boolean startServer() throws IOException;
	
	public abstract boolean stopServer() throws IOException;
	
}
