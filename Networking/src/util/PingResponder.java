package util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class PingResponder extends Thread {
	private DatagramSocket socket;
	
	public PingResponder(DatagramSocket socket) {
		setName("PingResponder");
		this.socket = socket;
	}

	@Override
	public void run() {
		while (!interrupted()) {
			byte[] buf = new byte[1];
			DatagramPacket p = new DatagramPacket(buf, buf.length);
			try {
				socket.receive(p);
				byte[] respondBuf = new byte[1];
				respondBuf[0] = 1;
				DatagramPacket rP = new DatagramPacket(respondBuf, respondBuf.length);
				rP.setAddress(p.getAddress());
				rP.setPort(p.getPort());
				socket.send(rP);
			} catch (IOException e) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
		super.run();
	}
}