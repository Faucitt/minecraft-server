package faucitt.io.packet;

import java.io.IOException;
import java.util.List;

import faucitt.io.MCSocket;
import faucitt.model.entity.EntityMetaData;


public class EntityMetaDataPacket extends Packet {

	private int entityId;
	private List<EntityMetaData> metaData;
	
	public EntityMetaDataPacket() {
		super((byte) 0x40);
	}

	@Override
	public void write(MCSocket socket) throws IOException {
		//TODO
	}
	
	public static EntityMetaDataPacket read(MCSocket socket) throws IOException {
		EntityMetaDataPacket packet = new EntityMetaDataPacket();
		packet.setEntityId(socket.readInt());
		packet.setMetaData(EntityMetaData.readEntityMetaData(socket));
		return packet;
	}

	public int getEntityId() {
		return entityId;
	}

	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

	public List<EntityMetaData> getMetaData() {
		return metaData;
	}

	public void setMetaData(List<EntityMetaData> metaData) {
		this.metaData = metaData;
	}

}
