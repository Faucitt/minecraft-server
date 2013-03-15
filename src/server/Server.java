package server;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.NoSuchPaddingException;

import server.io.MCSocket;
import server.model.Player;

public class Server {
	private ServerSocket connectionListener;
	private EntityHandler entityHandler;
	private int port;

	public static void main(String[] args) throws Exception {
		if (args.length < 1) throw new Exception("Incorrect arguments, expected: [port]");
		Server server = new Server(Integer.parseInt(args[0]));
		server.process();
	}
	
	public Server(int port) throws IOException {
		this.setPort(port);
		entityHandler = new EntityHandler();
		connectionListener = new ServerSocket(port);
	}
	
	public void process() throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
		//Start physics threads etc. here.

		//Connection listener thread.
		while (true) {
			Socket socket = connectionListener.accept();
			
			byte[] sharedSecret = new byte[16];
			SecureRandom rng = new SecureRandom();
			rng.nextBytes(sharedSecret);
			
			MCSocket mcsocket = new MCSocket(sharedSecret, socket.getInputStream(), socket.getOutputStream());
			
			Player player = new Player(mcsocket, this);
			entityHandler.addPlayer(player);
			
			Thread thread = new Thread(player);
			thread.start();
		}
	}

	public ServerSocket getConnectionListener() {
		return connectionListener;
	}

	public void setConnectionListener(ServerSocket connectionListener) {
		this.connectionListener = connectionListener;
	}

	public EntityHandler getEntityHandler() {
		return entityHandler;
	}

	public void setEntityHandler(EntityHandler entityHandler) {
		this.entityHandler = entityHandler;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
