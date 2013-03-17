package server.io.packet;

import java.io.IOException;

import server.io.MCSocket;

public class AnimationPacket extends Packet {

	public AnimationPacket() {
		super((byte) 0x12);
	}
	
	public enum Animation {
		NONE(0),
		SWING(1),
		DAMAGE(2),
		LEAVE_BED(3),
		EAT_FOOD(5),
		CROUCH(104),
		UNCROUCH(105);
		
		private static final Animation[] animations = new Animation[106];
		
		static {
			for (Animation animation : Animation.values()) {
				animations[animation.getId()] = animation;
			}
		}
		
		private final int id;
		
		Animation(int id) {
			this.id = id;
		}
		
		public int getId() {
			return id;
		}
		
		public static Animation getForId(int id) {
			return animations[id];
		}
	}

	private int entityId;
	private Animation animation;
	
	@Override
	public void write(MCSocket socket) throws IOException {
		socket.writeInt(entityId);
		socket.writeByte((byte) animation.getId());
	}
	
	public static AnimationPacket read(MCSocket socket) throws IOException {
		AnimationPacket packet = new AnimationPacket();
		packet.setEntityId(socket.readInt());
		packet.setAnimation(Animation.getForId(socket.readByte()));
		return packet;
	}

	public Animation getAnimation() {
		return animation;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

	public int getEntityId() {
		return entityId;
	}

	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

}
