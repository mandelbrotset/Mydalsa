package util.server;

import java.io.IOException;

public abstract class ClientHolder<E> extends Thread {
	protected abstract boolean write(E packet) throws IOException;
}
