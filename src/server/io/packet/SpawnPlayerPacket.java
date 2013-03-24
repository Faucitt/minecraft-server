package server.io.packet;

import java.io.IOException;

import server.io.MCSocket;
import server.model.entity.ByteMetaData;
import server.model.entity.EndMetaData;
import server.model.entity.EntityMetaData;

public class SpawnPlayerPacket extends Packet {

	public SpawnPlayerPacket() {
		super((byte) 0x14);
	}
	
	private int entityId, x, y, z;
	private byte yaw, pitch;
	private short itemId;
	private EntityMetaData metaData = new ByteMetaData((byte) 0, (byte) 0);
	private String name;

	@Override
	public void write(MCSocket socket) throws IOException {
		socket.writeInt(entityId);
		socket.writeString(name);
		socket.writeInt(x);
		socket.writeInt(y);
		socket.writeInt(z);
		socket.writeByte(yaw);
		socket.writeByte(pitch);
		socket.writeShort(itemId);
		metaData.write(socket);
		(new EndMetaData()).write(socket);
	}

	public int getEntityId() {
		return entityId;
	}

	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public void setX(double x) {
		this.x = (int) (x*32);
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setY(double y) {
		this.y = (int) (y*32);
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
	
	public void setZ(double z) {
		this.z = (int) (z*32);
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

	public short getItemId() {
		return itemId;
	}

	public void setItemId(short itemId) {
		this.itemId = itemId;
	}

	public EntityMetaData getMetaData() {
		return metaData;
	}

	public void setMetaData(EntityMetaData metaData) {
		this.metaData = metaData;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
