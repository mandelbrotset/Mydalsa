package udp.server;

import java.io.IOException;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import udp.Message;
import util.server.ClientHolder;

public class UDPClientHolder extends ClientHolder<Message> {

	@Override
	protected boolean write(Message packet) throws IOException {
		throw new NotImplementedException();
	}

	
}
