package faucitt.io.packet;

import java.io.IOException;

import faucitt.io.MCSocket;


public class DestroyEntitiesPacket extends Packet {

	public DestroyEntitiesPacket() {
		super((byte) 0x1D);
	}
	
	private int[] entityIds;

	@Override
	public void write(MCSocket socket) throws IOException {
		if (entityIds.length > 127) throw new IOException("Tried to destroy more than 127 entities in one packet.");
		
		socket.writeByte((byte) entityIds.length);
		for (int entityId : entityIds) {
			socket.writeInt(entityId);
		}
	}

	public int[] getEntityIds() {
		return entityIds;
	}

	public void setEntityIds(int[] entityIds) {
		this.entityIds = entityIds;
	}

}
