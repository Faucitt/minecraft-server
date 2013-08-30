package faucitt.io.packet;

import java.io.IOException;

import faucitt.io.MCSocket;


public class PositionPacket extends Packet {

	private double x, y, z, stance;
	private boolean flying;
	
	public PositionPacket() {
		super((byte) 0x0B);
	}

	@Override
	public void write(MCSocket socket) throws IOException {}
	
	public static PositionPacket read(MCSocket socket) throws IOException {
		PositionPacket packet = new PositionPacket();
		packet.setX(socket.readDouble());
		packet.setY(socket.readDouble());
		packet.setStance(socket.readDouble());
		packet.setZ(socket.readDouble());
		packet.setFlying(socket.readByte() == 0);
		return packet;
	}

	public boolean isFlying() {
		return flying;
	}

	public void setFlying(boolean flying) {
		this.flying = flying;
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

}
