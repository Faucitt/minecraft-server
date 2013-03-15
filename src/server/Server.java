package server;

import java.awt.EventQueue;
import java.net.ServerSocket;

public class Server {
	private ServerSocket connectionListener;

	public static void main(String[] args) {
		Server server = new Server(Integer.parseInt(args[0]));
		server.process();
	}
	
	public Server(int port) {
		
	}
	
	public void process() {
		//Physics thread.
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				//TODO
			}
		});

		//Connection listener thread.
		while (true) {
			//TODO
		}
	}

	public ServerSocket getConnectionListener() {
		return connectionListener;
	}

	public void setConnectionListener(ServerSocket connectionListener) {
		this.connectionListener = connectionListener;
	}

}
