package server.io.packet;

import java.io.IOException;

import server.io.MCSocket;

public class DiggingPacket extends Packet {

	public enum DiggingStatus {
		STARTED(0),
		CANCELLED(1),
		FINISHED(2),
		DROP_ITEM_STACK(3),
		DROP_ITEM(4),
		FINISH_ACTION(5);
		
		private static final DiggingStatus[] statuses = new DiggingStatus[6];
		
		static {
			for (DiggingStatus status : DiggingStatus.values()) {
				statuses[status.id] = status;
			}
		}
		
		private final int id;
		
		DiggingStatus(int id) {
			this.id = id;
		}
		
		public int getId() {
			return id;
		}
		
		public static DiggingStatus getForId(int id) {
			return statuses[id];
		}
	}
	
	public DiggingPacket() {
		super((byte) 0x0E);
	}

	@Override
	public void write(MCSocket socket) throws IOException {}
	
	private DiggingStatus status;
	private int x, y, z;
	private byte face;
	
	public static DiggingPacket read(MCSocket socket) throws IOException {
		DiggingPacket packet = new DiggingPacket();
		packet.setStatus(DiggingStatus.getForId(socket.readByte()));
		packet.setX(socket.readInt());
		packet.setY(socket.readByte());
		packet.setZ(socket.readInt());
		packet.setFace(socket.readByte());
		return packet;
	}

	public DiggingStatus getStatus() {
		return status;
	}

	public void setStatus(DiggingStatus status) {
		this.status = status;
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

	public byte getFace() {
		return face;
	}

	public void setFace(byte face) {
		this.face = face;
	}

}
