package server.io.packet;

import java.io.IOException;

import server.io.MCSocket;

public class TimePacket extends Packet {

	private long time;
	
	public TimePacket() {
		super((byte) 0x04);
	}
	
	public TimePacket(long time) {
		super((byte) 0x04);
		this.time = time;
	}

	@Override
	public void write(MCSocket socket) throws IOException {
		socket.writeLong(time);
		socket.writeLong(time%24000);
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

}
