package faucitt.io.packet;

import java.io.IOException;

import faucitt.io.MCSocket;


public class EntityMoveLookPacket extends Packet {

	public EntityMoveLookPacket() {
		super((byte) 0x21);
	}
	
	private int entityId;
	private byte yaw, pitch, dx, dy, dz;
	
	@Override
	public void write(MCSocket socket) throws IOException {
		socket.writeInt(entityId);
		socket.writeByte(dx);
		socket.writeByte(dy);
		socket.writeByte(dz);
		socket.writeByte(yaw);
		socket.writeByte(pitch);
	}
	
	public int getEntityId() {
		return entityId;
	}

	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}
	
	public byte getYaw() {
		return yaw;
	}

	public void setYaw(byte yaw) {
		this.yaw = yaw;
	}
	
	public void setYaw(float yaw) {
		this.yaw = (byte) ((256*yaw)/(Math.PI*2));
	}

	public byte getPitch() {
		return pitch;
	}

	public void setPitch(byte pitch) {
		this.pitch = pitch;
	}
	
	public void setPitch(float pitch) {
		this.pitch = (byte) ((256*pitch)/(Math.PI*2));
	}
	
	public byte getDx() {
		return dx;
	}

	public void setDx(byte dx) {
		this.dx = dx;
	}

	public byte getDy() {
		return dy;
	}

	public void setDy(byte dy) {
		this.dy = dy;
	}

	public byte getDz() {
		return dz;
	}

	public void setDz(byte dz) {
		this.dz = dz;
	}

}
