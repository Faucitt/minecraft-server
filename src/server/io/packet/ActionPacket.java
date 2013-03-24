package server.io.packet;

import java.io.IOException;

import server.io.MCSocket;

public class ActionPacket extends Packet {

	public ActionPacket() {
		super((byte) 0x13);
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
	public void write(MCSocket socket) throws IOException {}
	
	private int entityId;
	private Action action;
	
	public static ActionPacket read(MCSocket socket) throws IOException {
		ActionPacket packet = new ActionPacket();
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