package faucitt.io.packet;

import java.io.IOException;

import faucitt.io.MCSocket;


public class FlyingPacket extends Packet {

	private boolean flying;
	
	public FlyingPacket() {
		super((byte) 0x0A);
	}
	
	public FlyingPacket(boolean flying) {
		super((byte) 0x0A);
		setFlying(flying);
	}
	
	@Override
	public void write(MCSocket socket) throws IOException {
		socket.writeByte((byte) (flying? 0 : 1));
	}
	
	public static FlyingPacket read(MCSocket socket) throws IOException {
		FlyingPacket packet = new FlyingPacket();
		packet.setFlying(socket.readByte() == 0);
		return packet;
	}

	public boolean isFlying() {
		return flying;
	}

	public void setFlying(boolean flying) {
		this.flying = flying;
	}

}
