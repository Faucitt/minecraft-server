package server.packet;

import java.io.IOException;
import java.util.Random;

import server.io.MCSocket;

public class PingPacket extends Packet {
	private static Random rng = new Random();
	
	private int random;
	
	public PingPacket() {
		super((byte) 0x00);
	}

	@Override
	public void write(MCSocket socket) throws IOException {
		socket.writeInt(rng.nextInt());
	}

	@Override
	public Packet read(MCSocket socket) throws IOException {
		random = socket.readInt();
		return this;
	}

	public int getRandom() {
		return random;
	}

	public void setRandom(int random) {
		this.random = random;
	}
}
