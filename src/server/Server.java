package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.crypto.NoSuchPaddingException;

import server.io.MCSocket;
import server.model.Player;
import server.terrain.World;

public class Server {
	
	private ServerSocket connectionListener;
	private EntityHandler entityHandler;
	private Configuration configuration;
	private String serverId;
	private int port;
	private long time;
	
	private List<World> worlds = new ArrayList<World>();

	public static void main(String[] args) throws Exception {
		if (args.length < 1) throw new Exception("Incorrect arguments, expected: [port]");
		Server server = new Server(Integer.parseInt(args[0]));
		server.process();
	}
	
	public Server(int port) throws IOException {
		setPort(port);
		
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		serverId = "";
		Random rng = new Random();
		for (int i = 0; i < 16; i++) {
			int off = rng.nextInt(chars.length());
			serverId += chars.substring(off, off+1);
		}
		
		Configuration config = new Configuration();
		configuration = config;
		
		entityHandler = new EntityHandler();
		connectionListener = new ServerSocket(port);
		
		worlds.add(new World(0, 8, 8));
		worlds.get(0).calculateLight();
		worlds.get(0).calculateSunlight();
		System.out.println("Done lighting.");
	}
	
	public void process() throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
		//Start physics threads etc. here.
		Thread tickThread = new Thread(new TickHandler(this));
		tickThread.start();
		
		//Connection listener thread.
		while (true) {
			Socket socket = connectionListener.accept();
			
			//1 minute timeout.
			socket.setSoTimeout(60000);
			
			byte[] sharedSecret = new byte[16];
			SecureRandom rng = new SecureRandom();
			rng.nextBytes(sharedSecret);
			
			MCSocket mcsocket = new MCSocket(sharedSecret, socket.getInputStream(), socket.getOutputStream());
			
			Player player = new Player(mcsocket, this);
			
			Thread thread = new Thread(player);
			thread.start();
		}
	}
	
	public String getServerId() {
		return serverId;
	}
	
	public void setServerId(String serverId) {
		this.serverId = serverId;
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

	public List<World> getWorlds() {
		return worlds;
	}

	public void setWorlds(List<World> worlds) {
		this.worlds = worlds;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	public void incrementTime() {
		time++;
	}

}
