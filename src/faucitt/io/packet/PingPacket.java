package faucitt.io.packet;

import java.io.IOException;
import java.util.Random;

import faucitt.io.MCSocket;


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

	public static PingPacket read(MCSocket socket) throws IOException {
		PingPacket packet = new PingPacket();
		packet.setRandom(socket.readInt());
		return packet;
	}

	public int getRandom() {
		return random;
	}

	public void setRandom(int random) {
		this.random = random;
	}
}
