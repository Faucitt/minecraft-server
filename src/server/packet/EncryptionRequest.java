package server.packet;

import java.io.IOException;

import server.io.MCSocket;

public class EncryptionRequest extends Packet {

	private byte[] sharedSecret, verificationToken;
	
	public EncryptionRequest() {
		super((byte) 0xFD);
	}
	
	public EncryptionRequest(byte[] sharedSecret, byte[] verificationToken) {
		super((byte) 0xFD);
		setSharedSecret(sharedSecret);
		setVerificationToken(verificationToken);
	}

	@Override
	public void write(MCSocket socket) throws IOException {
		socket.writeByteArray(getSharedSecret());
		socket.writeByteArray(getVerificationToken());
	}

	@Override
	public Packet read(MCSocket socket) throws IOException {
		throw new IOException("Unexpected packet, 0xFD - EncryptionRequest");
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
