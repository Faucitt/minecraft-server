package server.packet;

import java.io.IOException;
import java.util.Random;

import server.io.MCSocket;

public class PingPacket extends Packet {
	private static Random rng = new Random();
	
	public PingPacket() {
		super((byte) 0x00);
	}

	@Override
	public byte[] write(MCSocket socket) throws IOException {
		socket.writeInt(rng.nextInt());
		return null;
	}

	@Override
	public Packet read(MCSocket socket) throws IOException {
		socket.readInt();
		return null;
	}
}
