package server.packet;

import java.io.IOException;

import server.io.MCSocket;

public class OpenWindowPacket extends Packet {

	private byte windowId, slots;
	private String title;
	private boolean useTitle;
	
	
	public OpenWindowPacket() {
		super((byte) 0x64);
	}

	@Override
	public void write(MCSocket socket) throws IOException {}

	public byte getWindowId() {
		return windowId;
	}

	public void setWindowId(byte windowId) {
		this.windowId = windowId;
	}

	public byte getSlots() {
		return slots;
	}

	public void setSlots(byte slots) {
		this.slots = slots;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isUseTitle() {
		return useTitle;
	}

	public void setUseTitle(boolean useTitle) {
		this.useTitle = useTitle;
	}

}
