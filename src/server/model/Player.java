package server.model;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import server.io.MCSocket;
import server.packet.Packet;

public class Player extends Entity {
	private MCSocket socket;
	private String username;
	private Queue<Packet> packetQueue = new ArrayBlockingQueue<Packet>(4096);
	
	public Player(MCSocket socket) {
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
	
	public void sendPacket() throws IOException {
		Packet packet = popPacket();
		if (packet != null) {
			socket.writeByte(packet.getId());
			packet.write(socket);
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
