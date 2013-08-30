package faucitt.io.packet;

import java.io.IOException;

import faucitt.io.MCSocket;


public class SettingsPacket extends Packet {

	private String language;
	private byte viewDistance, chatMode, difficulty;
	private boolean showCape;
	
	public SettingsPacket() {
		super((byte) 0xCC);
	}

	@Override
	public void write(MCSocket socket) throws IOException {}
	
	public static SettingsPacket read(MCSocket socket) throws IOException {
		SettingsPacket packet = new SettingsPacket();
		packet.setLanguage(socket.readString());
		packet.setViewDistance(socket.readByte());
		packet.setChatMode(socket.readByte());
		packet.setDifficulty(socket.readByte());
		packet.setShowCape(socket.readByte() == 1);
		return packet;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public byte getViewDistance() {
		return viewDistance;
	}

	public void setViewDistance(byte viewDistance) {
		this.viewDistance = viewDistance;
	}

	public byte getChatMode() {
		return chatMode;
	}

	public void setChatMode(byte chatMode) {
		this.chatMode = chatMode;
	}

	public byte getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(byte difficulty) {
		this.difficulty = difficulty;
	}

	public boolean isShowCape() {
		return showCape;
	}

	public void setShowCape(boolean showCape) {
		this.showCape = showCape;
	}

}
