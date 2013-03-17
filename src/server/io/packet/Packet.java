package server.io.packet;

import java.io.IOException;

import server.io.MCSocket;


public abstract class Packet {
	public abstract void write(MCSocket socket) throws IOException;
	//public abstract Packet read(MCSocket socket) throws IOException;
	
	private byte id;
	
	public Packet(byte id) {
		setId(id);
	}
	
	public byte getId() {
		return id;
	}
	public void setId(byte id) {
		this.id = id;
	}
}
