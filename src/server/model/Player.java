package server.model;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import server.Server;
import server.io.MCSocket;
import server.packet.Packet;

public class Player extends Entity implements Runnable {
	private MCSocket socket;
	private String username;
	private Server server;
	private Queue<Packet> packetQueue = new ArrayBlockingQueue<Packet>(4096);
	
	public Player(MCSocket socket, Server server) {
		setSocket(socket);
	}

	public MCSocket getSocket() {
		return socket;
	}

	public void setSocket(MCSocket socket) {
		this.socket = socket;
	}
	
	public void pushPacket(Packet packet) {
		packetQueue.add(packet);
	}
	
	public Packet popPacket() {
		return packetQueue.poll();
	}
	
	public boolean sendPacket() throws IOException {
		Packet packet = popPacket();
		if (packet != null) {
			socket.writeByte(packet.getId());
			packet.write(socket);
			return true;
		}
		return false;
	}
	
	public boolean receivePacket() throws IOException {
		return false;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		boolean keepRunning = true;
		while (keepRunning) {
			try {
				if (!(sendPacket() || receivePacket())) Thread.currentThread().sleep(10);
			} catch (IOException | InterruptedException e) {
				server.getEntityHandler().removePlayer(this);
				keepRunning = false;
			}
		}
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}
}
