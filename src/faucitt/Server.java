package faucitt;

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

import faucitt.events.Event;
import faucitt.events.listeners.Listener;
import faucitt.io.MCSocket;
import faucitt.logging.Logger;
import faucitt.model.Player;
import faucitt.terrain.World;

public class Server {

	private ServerSocket connectionListener;
	private EntityHandler entityHandler;
	private static Configuration configuration;
	private String serverId;
	private long time;
	public static final Logger logger = Logger.getLogger("Faucitt");

	private List<World> worlds = new ArrayList<World>();

	List<Listener> listeners = new ArrayList<Listener>();

	public void addListener(Listener listener) {
		listeners.add(listener);
	}

	public void removeXXXListener(Listener listener) {
		listeners.remove(listener);
	}

	protected void fireEvent(Event e) {
		Object[] ls = listeners.toArray();

		int numListeners = ls.length;
		for (int i = 0; i < numListeners; i += 2) {
			if (ls[i] == Listener.class) {
				((Listener) ls[i + 1]).Dispatch(e);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		logger.log("Running " + Versioning.getName() + " "
				+ Versioning.getVersion());
		logger.log("Starting server..");

		configuration = new Configuration();

		Server.logger.log(">>  Port: " + configuration.getPort());
		Server.logger.log(">>  Max-Players: " + configuration.getMaxPlayers());
		Server.logger.log(">>  Chunk Range: " + configuration.getChunkRange());
		Server.logger.log(">>  Message of the Day: " + configuration.getMotd());

		Server server = new Server();
		server.process();
	}

	public Server() throws IOException {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		serverId = "";
		Random rng = new Random();
		for (int i = 0; i < 16; i++) {
			int off = rng.nextInt(chars.length());
			serverId += chars.substring(off, off + 1);
		}

		entityHandler = new EntityHandler();
		connectionListener = new ServerSocket(configuration.getPort());

		Server.logger.log("Listening on port " + configuration.getPort() + "..");

		worlds.add(new World(0, 8, 8));
		Server.logger.log("Loaded world..");
		worlds.get(0).calculateLight();
		worlds.get(0).calculateSunlight();
		logger.log("Finished calculating light");
		Server.logger.log("Startup complete!");
	}

	public void process() throws IOException, InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException {
		// Start physics threads etc. here.
		Thread tickThread = new Thread(new TickHandler(this));
		tickThread.start();
		Server.logger.log("Now accepting connections..");

		// Connection listener thread.
		while (true) {
			Socket socket = connectionListener.accept();

			// 10 second timeout.
			socket.setSoTimeout(10000);

			byte[] sharedSecret = new byte[16];
			SecureRandom rng = new SecureRandom();
			rng.nextBytes(sharedSecret);

			MCSocket mcsocket = new MCSocket(sharedSecret,
					socket.getInputStream(), socket.getOutputStream());

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
