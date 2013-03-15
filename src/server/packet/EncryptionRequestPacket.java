package server.packet;

import java.io.IOException;

import server.io.MCSocket;

public class EncryptionRequestPacket extends Packet {

	private byte[] publicKey, verificationToken;
	private String serverId;
	
	public EncryptionRequestPacket() {
		super((byte) 0xFD);
	}
	
	public EncryptionRequestPacket(String serverId, byte[] publicKey, byte[] verificationToken) {
		super((byte) 0xFD);
		setPublicKey(publicKey);
		setVerificationToken(verificationToken);
		setServerId(serverId);
	}

	@Override
	public void write(MCSocket socket) throws IOException {
		socket.writeString(serverId);
		socket.writeByteArray(getPublicKey());
		socket.writeByteArray(getVerificationToken());
	}

	public static EncryptionRequestPacket read(MCSocket socket) throws IOException {
		throw new IOException("Unexpected packet, 0xFD - EncryptionRequest");
	}

	public byte[] getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(byte[] key) {
		this.publicKey = key;
	}

	public byte[] getVerificationToken() {
		return verificationToken;
	}

	public void setVerificationToken(byte[] verificationToken) {
		this.verificationToken = verificationToken;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

}
