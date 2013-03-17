package server.io.packet;

import java.io.IOException;

import server.io.MCSocket;

public class EncryptionResponsePacket extends Packet {
	
	private byte[] sharedSecret, verificationToken;

	public EncryptionResponsePacket() {
		super((byte) 0xFC);
	}
	
	public EncryptionResponsePacket(byte[] sharedSecret, byte[] verificationToken) {
		super((byte) 0xFC);
		setSharedSecret(sharedSecret);
		setVerificationToken(verificationToken);
	}

	@Override
	public void write(MCSocket socket) throws IOException {
		socket.writeByteArray(getSharedSecret());
		socket.writeByteArray(getVerificationToken());
	}

	public static EncryptionResponsePacket read(MCSocket socket) throws IOException {
		EncryptionResponsePacket packet = new EncryptionResponsePacket();
		packet.setSharedSecret(socket.readByteArray());
		packet.setVerificationToken(socket.readByteArray());
		return packet;
	}

	public byte[] getSharedSecret() {
		return sharedSecret;
	}

	public void setSharedSecret(byte[] sharedSecret) {
		this.sharedSecret = sharedSecret;
	}

	public byte[] getVerificationToken() {
		return verificationToken;
	}

	public void setVerificationToken(byte[] verificationToken) {
		this.verificationToken = verificationToken;
	}
}
