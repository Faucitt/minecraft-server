package server.packet;

import java.io.IOException;

import server.io.MCSocket;

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
		socket.writeByte((byte) (flying? 1 : 0));
	}

	public boolean isFlying() {
		return flying;
	}

	public void setFlying(boolean flying) {
		this.flying = flying;
	}

}
