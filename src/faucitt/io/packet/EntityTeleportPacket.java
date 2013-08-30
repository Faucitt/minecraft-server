package faucitt.io.packet;

import java.io.IOException;

import faucitt.io.MCSocket;


public class EntityTeleportPacket extends Packet {

	public EntityTeleportPacket() {
		super((byte) 0x22);
	}
	
	private int entityId, x, y, z;
	private byte yaw, pitch;

	@Override
	public void write(MCSocket socket) throws IOException {
		socket.writeInt(entityId);
		socket.writeInt(x);
		socket.writeInt(y);
		socket.writeInt(z);
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

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

}
