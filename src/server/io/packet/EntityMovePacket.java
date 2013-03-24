package server.io.packet;

import java.io.IOException;

import server.io.MCSocket;

public class EntityMovePacket extends Packet {

	public EntityMovePacket() {
		super((byte) 0x1F);
	}
	
	private int entityId;
	private byte dx, dy, dz;

	@Override
	public void write(MCSocket socket) throws IOException {
		socket.writeInt(entityId);
		socket.writeByte(dx);
		socket.writeByte(dy);
		socket.writeByte(dz);
	}

	public int getEntityId() {
		return entityId;
	}

	public void setEntityId(int entityId) {
		this.entityId = entityId;
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
