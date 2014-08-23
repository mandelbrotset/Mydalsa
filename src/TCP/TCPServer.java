package TCP;
import java.io.IOException;
import java.net.*;
import Util.Server;

public class TCPServer extends Server<Object> {
	private ServerSocket socket;
	
	public TCPServer(TCPPacketReceiver packetReceiver, int port) {
		super(packetReceiver, port);
		setName(this.getClass().getName());
	}

	@Override
	public boolean startServer() throws IOException {
		socket = new ServerSocket(port);
		start();
		return true;
	}

	@Override
	public boolean stopServer() throws IOException {
		socket.close();
		return true;
	}

	@Override
	public void run() {
		while (!interrupted()) {
			try {
				Socket clientSocket = socket.accept();
				TCPClientHolder tcpch = new TCPClientHolder(clientSocket, (TCPPacketReceiver)packetReceiver);
				synchronized (clients) {
					clients.add(tcpch);
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		super.run();
	}

}
