package faucitt.io;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Cryptography {
	public static byte[] getServerIdHash(String str, PublicKey key, SecretKey secretKey) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return digestOperation("SHA-1", new byte[][] {str.getBytes("ISO_8859_1"), secretKey.getEncoded(), key.getEncoded()});
	}
	
	private static byte[] digestOperation(String instance, byte[] ... data) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance(instance);
		byte[][] dat = data;
		int length = data.length;
		for (int i = 0; i < length; ++i)
		{
			byte[] ddat = dat[i];
			digest.update(ddat);
		}
		return digest.digest();
	}
	
	public static SecretKey decryptSharedKey(PrivateKey key, byte[] data) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
		return new SecretKeySpec(decryptData(key, data), "AES");
	}
	
	public static byte[] decryptData(Key key, byte[] data) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
		return cipherOperation(2, key, data);
	}
	
	private static byte[] cipherOperation(int i, Key key, byte[] data) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
		return createCipherInstance(i, key.getAlgorithm(), key).doFinal(data);
	}
	
	private static Cipher createCipherInstance(int i, String s, Key key) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
		Cipher cipher = Cipher.getInstance(s);
		cipher.init(i, key);
		return cipher;
	}
}
