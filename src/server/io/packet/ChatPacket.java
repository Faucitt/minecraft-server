package server.io.packet;

import java.io.IOException;

import server.io.MCSocket;

public class ChatPacket extends Packet {
	private String message;

	public ChatPacket() {
		super((byte) 0x03);
	}
	
	public ChatPacket(String message) {
		super((byte) 0x03);
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
	
	public static ChatPacket read(MCSocket socket) throws IOException {
		ChatPacket packet = new ChatPacket();
		packet.setMessage(socket.readString().replace("§", ""));
		return packet;
	}
}
