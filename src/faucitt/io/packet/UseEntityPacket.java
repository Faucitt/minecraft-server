package faucitt.io.packet;

import java.io.IOException;

import faucitt.io.MCSocket;


public class UseEntityPacket extends Packet {

	private int entityId, targetId;
	private boolean leftClick;
	
	public UseEntityPacket() {
		super((byte) 0x07);
	}

	@Override
	public void write(MCSocket socket) throws IOException {}
	
	public static UseEntityPacket read(MCSocket socket) throws IOException {
		UseEntityPacket packet = new UseEntityPacket();
		packet.setEntityId(socket.readInt());
		packet.setTargetId(socket.readInt());
		packet.setLeftClick(socket.readByte() == 1);
		return packet;
	}

	public int getEntityId() {
		return entityId;
	}

	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

	public int getTargetId() {
		return targetId;
	}

	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}

	public boolean isLeftClick() {
		return leftClick;
	}

	public void setLeftClick(boolean leftClick) {
		this.leftClick = leftClick;
	}

}
