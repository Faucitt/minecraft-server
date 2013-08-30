package faucitt.io.packet;

import java.io.IOException;

import faucitt.io.MCSocket;


public class LoginPacket extends Packet {

	private int entityId;
	private String levelType = "default";
	private byte gameMode, dimension, difficulty, maxPlayers;
	
	public LoginPacket() {
		super((byte) 0x01);
	}
	
	public LoginPacket(int entityId) {
		super((byte) 0x01);
		setEntityId(entityId);
	}

	@Override
	public void write(MCSocket socket) throws IOException {
		socket.writeInt(entityId);
		socket.writeString(levelType);
		socket.writeByte(gameMode);
		socket.writeByte(dimension);
		socket.writeByte(difficulty);
		socket.writeByte((byte) 0);//?
		socket.writeByte(maxPlayers);
	}

	public int getEntityId() {
		return entityId;
	}

	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

	public String getLevelType() {
		return levelType;
	}

	public void setLevelType(String levelType) {
		this.levelType = levelType;
	}

	public byte getGameMode() {
		return gameMode;
	}

	public void setGameMode(byte gameMode) {
		this.gameMode = gameMode;
	}

	public byte getDimension() {
		return dimension;
	}

	public void setDimension(byte dimension) {
		this.dimension = dimension;
	}

	public byte getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(byte difficulty) {
		this.difficulty = difficulty;
	}

	public byte getMaxPlayers() {
		return maxPlayers;
	}

	public void setMaxPlayers(byte maxPlayers) {
		if (maxPlayers > 64) maxPlayers = (byte) 64;
		this.maxPlayers = maxPlayers;
	}

}
