package server.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MCSocket {
	private DataInputStream encryptedInput;
	private DataInputStream unencryptedInput;
	
	private DataOutputStream encryptedOutput;
	private DataOutputStream unencryptedOutput;
	
	private boolean encryptionEnabled;
	
	private byte[] sharedSecret;
	
	public MCSocket(byte[] sharedSecret, InputStream in, OutputStream out) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		unencryptedInput = new DataInputStream(in);
		unencryptedOutput = new DataOutputStream(out);
		
		setSharedSecret(sharedSecret);
		
		SecretKey key = new SecretKeySpec(sharedSecret, "AES");
		IvParameterSpec iv = new IvParameterSpec(sharedSecret);
		
		Cipher cipherInput = Cipher.getInstance("AES/CFB8/NoPadding");
		cipherInput.init(Cipher.DECRYPT_MODE, key, iv);
		encryptedInput = new DataInputStream(new CipherInputStream(in, cipherInput));
		
		Cipher cipherOutput = Cipher.getInstance("AES/CFB8/NoPadding");
		cipherOutput.init(Cipher.ENCRYPT_MODE, key, iv);
		encryptedOutput = new DataOutputStream(new CipherOutputStream(out, cipherOutput));
	}
	
	public void enableEncryption(SecretKey key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		IvParameterSpec iv = new IvParameterSpec(key.getEncoded(), 0, 16);
		
		Cipher cipherInput = Cipher.getInstance("AES/CFB8/NoPadding");
		cipherInput.init(Cipher.DECRYPT_MODE, key, iv);
		encryptedInput = new DataInputStream(new CipherInputStream(unencryptedInput, cipherInput));
		
		Cipher cipherOutput = Cipher.getInstance("AES/CFB8/NoPadding");
		cipherOutput.init(Cipher.ENCRYPT_MODE, key, iv);
		encryptedOutput = new DataOutputStream(new CipherOutputStream(unencryptedOutput, cipherOutput));
		setEncryptionEnabled(true);
	}
	
	
	public byte[] readData(int len) throws IOException {
		byte[] data = new byte[len];
		getInputStream().readFully(data, 0, len);
		return data;
	}
	
	public void writeData(byte[] data) throws IOException {
		getOutputStream().write(data);
	}
	
	public void writeByteArray(byte[] data) throws IOException {
		writeShort((short) data.length);
		if (data.length > 32767) throw new IOException("Array too large.");
		writeData(data);
	}
	
	public byte[] readByteArray() throws IOException {
		short length = readShort();
		if (length < 0) throw new IOException("Array length less than 0.");
		return readData(length);
	}
	
	public byte readByte() throws IOException {
		return getInputStream().readByte();
	}
	
	public short readShort() throws IOException {
		return getInputStream().readShort();
	}
	
	public int readInt() throws IOException {
		return getInputStream().readInt();
	}
	
	public long readLong() throws IOException {
		return getInputStream().readLong();
	}
	
	public float readFloat() throws IOException {
		return getInputStream().readFloat();
	}
	
	public double readDouble() throws IOException {
		return getInputStream().readDouble();
	}
	
	public void writeByte(byte i) throws IOException {
		getOutputStream().writeByte(i);
	}
	
	public void writeShort(short i) throws IOException {
		getOutputStream().writeShort(i);
	}
	
	public void writeInt(int i) throws IOException {
		getOutputStream().writeInt(i);
	}
	
	public void writeLong(long i) throws IOException {
		getOutputStream().writeLong(i);
	}
	
	public void writeFloat(float i) throws IOException {
		getOutputStream().writeFloat(i);
	}
	
	public void writeDouble(double i) throws IOException {
		getOutputStream().writeDouble(i);
	}
	
	public String readString() throws IOException {
		int length = readShort();
		if (length > 256) throw new IOException("String length > 256.");
		byte[] data = readData(length*2);
		String string = new String(data, "UTF-16");
		return string;
	}
	
	public void writeString(String s) throws IOException {
		if (s.length() > 256) throw new IOException("String length > 256.");
		writeShort((short) s.length());
		getOutputStream().writeChars(s);
	}
	
	public DataInputStream getInputStream() {
		if (encryptionEnabled) return encryptedInput;
		return unencryptedInput;
	}
	
	public DataOutputStream getOutputStream() {
		if (encryptionEnabled) return encryptedOutput;
		return unencryptedOutput;
	}

	public boolean isEncryptionEnabled() {
		return encryptionEnabled;
	}

	public void setEncryptionEnabled(boolean encryptionEnabled) {
		this.encryptionEnabled = encryptionEnabled;
	}
	
	public void close() throws IOException {
		unencryptedInput.close();
		unencryptedOutput.close();
		encryptedInput.close();
		encryptedOutput.close();
	}

	public byte[] getSharedSecret() {
		return sharedSecret;
	}

	public void setSharedSecret(byte[] sharedSecret) {
		this.sharedSecret = sharedSecret;
	}
}
