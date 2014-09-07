package general;
public class IDGenerator {
	private static int id;
	
	static {
		id = 0;
	}
	
	public static int getNewID() {
		return id++;
	}
}
