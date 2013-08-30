package faucitt.io.packet;

import java.io.IOException;

import faucitt.io.MCSocket;


public class HandshakePacket extends Packet {

	public HandshakePacket() {
		super((byte) 0x02);
	}
	private String username, host;
	private int port;
	private byte protocolVersion;
	
	@Override
	public void write(MCSocket socket) throws IOException {}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public byte getProtocolVersion() {
		return protocolVersion;
	}

	public void setProtocolVersion(byte protocolVersion) {
		this.protocolVersion = protocolVersion;
	}
	
	public static HandshakePacket read(MCSocket socket) throws IOException {
		HandshakePacket packet = new HandshakePacket();
		packet.setProtocolVersion(socket.readByte());
		packet.setUsername(socket.readString());
		packet.setHost(socket.readString());
		socket.readShort();//?
		packet.setPort(socket.readShort());
		return packet;
	}

}
