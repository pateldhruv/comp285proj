package server;

public abstract class Server {

	protected int port;
	
	public Server(int port) {
		this.port = port;
	}
	
	public abstract void run() throws Exception;
	
}
