package server.io.packet;

import java.io.IOException;

import server.io.MCSocket;

public class DisconnectPacket extends Packet {
	
	private String message;

	public DisconnectPacket() {
		super((byte) 0xFF);
	}
	
	public DisconnectPacket(String message) {
		super((byte) 0xFF);
		setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public void write(MCSocket socket) throws IOException {
		socket.writeString(message);
	}
	
	public DisconnectPacket read(MCSocket socket) throws IOException {
		DisconnectPacket packet = new DisconnectPacket();
		packet.setMessage(socket.readString());
		return packet;
	}

}
