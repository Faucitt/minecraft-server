package faucitt.io.packet;

import java.io.IOException;

import faucitt.io.MCSocket;


public class HeadYawPacket extends Packet {

	public HeadYawPacket() {
		super((byte) 0x23);
	}

	private int entityId;
	private byte yaw;
	
	@Override
	public void write(MCSocket socket) throws IOException {
		socket.writeInt(entityId);
		socket.writeByte(yaw);
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
	
}
