package Util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class PingResponder extends Thread {
	private DatagramSocket socket;
	
	public PingResponder(DatagramSocket socket) {
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
				DatagramPacket rP = new DatagramPacket(respondBuf, respondBuf.length);
				rP.setAddress(p.getAddress());
				rP.setPort(p.getPort());
				socket.send(rP);
			} catch (IOException e) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
		super.run();
	}
	
	
}
