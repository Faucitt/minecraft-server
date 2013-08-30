package faucitt.io.packet;

import java.io.IOException;

import faucitt.io.MCSocket;


public class EntityActionPacket extends Packet {

	public EntityActionPacket() {
		super((byte) PacketID.EntityAction.getId());
	}
	
	public enum Action {
		NONE(0),
		CROUCH(1),
		UNCROUCH(2),
		LEAVE_BED(3),
		START_SPRINTING(4),
		STOP_SPRINTING(5);
		
		private static final Action[] actions = new Action[106];
		
		static {
			for (Action action : Action.values()) {
				actions[action.getId()] = action;
			}
		}
		
		private final int id;
		
		Action(int id) {
			this.id = id;
		}
		
		public int getId() {
			return id;
		}
		
		public static Action getForId(int id) {
			return actions[id];
		}
	}

	@Override
	public void write(MCSocket socket) throws IOException {
		socket.writeInt(getEntityId());
		socket.writeInt(action.getId());
	}
	
	private int entityId;
	private Action action;
	
	public static EntityActionPacket read(MCSocket socket) throws IOException {
		EntityActionPacket packet = new EntityActionPacket();
		packet.setEntityId(socket.readInt());
		packet.setAction(Action.getForId(socket.readByte()));
		return packet;
	}

	public int getEntityId() {
		return entityId;
	}

	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

}
