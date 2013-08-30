package faucitt.io.packet;

import java.io.IOException;

import faucitt.io.MCSocket;


public class PositionLookPacket extends Packet {

	private double x, y, z, stance;
	private float yaw, pitch;
	private boolean flying;
	
	public PositionLookPacket() {
		super((byte) 0x0D);
	}

	@Override
	public void write(MCSocket socket) throws IOException {}
	
	public static PositionLookPacket read(MCSocket socket) throws IOException {
		PositionLookPacket packet = new PositionLookPacket();
		packet.setX(socket.readDouble());
		packet.setY(socket.readDouble());
		packet.setStance(socket.readDouble());
		packet.setZ(socket.readDouble());
		packet.setYaw(socket.readFloat());
		packet.setPitch(socket.readFloat());
		packet.setFlying(socket.readByte() == 0);
		return packet;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public double getStance() {
		return stance;
	}

	public void setStance(double stance) {
		this.stance = stance;
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
