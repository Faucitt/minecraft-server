package server.io.packet;

import java.io.IOException;

import server.io.MCSocket;

public class StatusPacket extends Packet {
	
	public enum ClientStatus {
		SPAWN(0),
		RESPAWN(1);
		
		private static final ClientStatus[] statuses = new ClientStatus[2];
		
		static {
			for (ClientStatus status : ClientStatus.values()) {
				statuses[status.id] = status;
			}
		}
		
		private final int id;
		
		ClientStatus(int id) {
			this.id = id;
		}
		
		public int getId() {
			return id;
		}
		
		public static ClientStatus getForId(int id) {
			return statuses[id];
		}
	}

	private ClientStatus status;
	
	public StatusPacket() {
		super((byte) 0xCD);
	}

	@Override
	public void write(MCSocket socket) throws IOException {}
	
	public static StatusPacket read(MCSocket socket) throws IOException {
		StatusPacket packet = new StatusPacket();
		packet.setStatus(ClientStatus.values()[socket.readByte()]);
		return packet;
	}

	public ClientStatus getStatus() {
		return status;
	}

	public void setStatus(ClientStatus status) {
		this.status = status;
	}

}
