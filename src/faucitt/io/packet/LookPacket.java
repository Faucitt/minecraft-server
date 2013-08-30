package faucitt.io.packet;

import java.io.IOException;

import faucitt.io.MCSocket;


public class LookPacket extends Packet {

	private float yaw, pitch;
	private boolean flying;
	
	public LookPacket() {
		super((byte) 0x0C);
	}

	@Override
	public void write(MCSocket socket) throws IOException {}
	
	public static LookPacket read(MCSocket socket) throws IOException {
		LookPacket packet = new LookPacket();
		packet.setYaw(socket.readFloat());
		packet.setPitch(socket.readFloat());
		packet.setFlying(socket.readByte() == 0);
		return packet;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public boolean isFlying() {
		return flying;
	}

	public void setFlying(boolean flying) {
		this.flying = flying;
	}

}
