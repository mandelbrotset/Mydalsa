package Logging;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Handles logging with non-blocking methods. For Linux, standard syslog is used.
 *
 *
 */
public class Logger extends Thread {
	private static Logger instance = null;
	private String currentApplicationName;
	private long logInterval;
	private LinkedBlockingQueue<String> messageQueue;
	private LogSystem logSystem;
	
	private Logger() {
		setName("Logger");
		setDaemon(true);
		currentApplicationName = "";
		logInterval = 100;
		messageQueue = new LinkedBlockingQueue<String>();
		logSystem = LogSystem.Linux;
	}
	
	/**
	 * Singleton! First call creates an instance and start the thread.
	 */
	public static synchronized Logger getInstance() {
		if (instance == null) {
			instance = new Logger();
			instance.start();
		}
		return instance;
	}
	
	public void setApplicationName(String name) {
		this.currentApplicationName = name;
	}
	
	public String getCurrentApplicationName() {
		return currentApplicationName;
	}
	
	public void setLogInterval(long milliseconds) {
		logInterval = milliseconds;
	}
	
	public void setLogSystem(LogSystem logSystem) {
		this.logSystem = logSystem;
	}
	
	public void write(String applicationName, LogLevel logLevel, String message) {
		messageQueue.add(applicationName + "." + logLevel.toString() + " | " + message);
	}
	
	/**
	 * Writes a message using current application name and log level informational.
	 */
	public void write(String message) {
		write(currentApplicationName, LogLevel.info, message);
	}
	
	/**
	 * Writes a message using current application name.
	 */
	public void write(LogLevel logLevel, String message) {
		write(currentApplicationName, logLevel, message);
	}
	
	/**
	 * Writes a message using current application name.
	 */
	public void write(LogLevel logLevel, String message, String threadName) {
		write(currentApplicationName + "." + threadName, logLevel, message);
	}
	
	@Override
	public void run() {
		while (!interrupted()) {
			String message;
			while ((message = messageQueue.poll()) != null) {
				if (logSystem == LogSystem.Linux) {
					try {
						Runtime.getRuntime().exec("logger " + message);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					throw new NotImplementedException();
				}
			}

			try {
				Thread.sleep(logInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
