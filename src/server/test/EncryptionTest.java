package server.test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import server.io.MCSocket;

public class EncryptionTest {
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IOException {
		ServerSocket sock = new ServerSocket(25565);
		while (true) {
			try {
				Socket connection = sock.accept();
				MCSocket s = new MCSocket(new byte[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, new DataInputStream(connection.getInputStream()), new DataOutputStream(connection.getOutputStream()));
				
				System.out.println(s.readByte());
				System.out.println("Version: " + s.readByte());
				System.out.println(s.readString());
				System.out.println(s.readString() + ":" + s.readInt());
				System.out.println(s.readByte() + ", " + s.readShort());
				
				KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
				kpg.initialize(1024);
				KeyPair kp = kpg.generateKeyPair();
				PublicKey pubk = kp.getPublic();
				PrivateKey privkey = kp.getPrivate();
				System.out.println(Arrays.toString(privkey.getEncoded()));
				String serverid = "abcdef123456";
				
				//String var1 = (new BigInteger(CryptManager.getServerIdHash(NetLoginHandler.getServerId(this.loginHandler), NetLoginHandler.getLoginMinecraftServer(this.loginHandler).getKeyPair().getPublic(), NetLoginHandler.getSharedKey(this.loginHandler)))).toString(16);
				
				s.writeByte((byte)0xFD);
				s.writeString(serverid);
				byte[] privenc = pubk.getEncoded();
				System.out.println("len: " + privenc.length);
				s.writeShort((short)privenc.length);
				s.writeData(privenc);
				byte[] verifyToken = new byte[] {0x01, 0x02, 0x03, 0x04};
				s.writeShort((short) 4);
				s.writeData(verifyToken);
				
				System.out.println(s.readByte());
				short len = s.readShort();
				System.out.println("ln " + len);
				byte[] dat = s.readData(len);
				System.out.println(Arrays.toString(dat));
				SecretKey shared = decryptSharedKey(privkey, dat);
				String var1 = (new BigInteger(getServerIdHash(serverid, pubk, shared))).toString(16);
				System.out.println(var1);
				short len2 = s.readShort();
				byte[] encVer = s.readData(len2);
				
				s.writeByte((byte) 0xFC);
				s.writeShort((short)0);
				s.writeShort((short)0);
				
				s.enableEncryption(shared);
				
				System.out.println("YES: " + s.readByte());
			} catch (Exception ex) {
				System.out.println();
				ex.printStackTrace();
			}
		}
	}
	
    public static byte[] getServerIdHash(String par0Str, PublicKey par1PublicKey, SecretKey par2SecretKey)
    {
        try
        {
            return digestOperation("SHA-1", new byte[][] {par0Str.getBytes("ISO_8859_1"), par2SecretKey.getEncoded(), par1PublicKey.getEncoded()});
        }
        catch (UnsupportedEncodingException var4)
        {
            var4.printStackTrace();
            return null;
        }
    }
    
    private static byte[] digestOperation(String par0Str, byte[] ... par1ArrayOfByte)
    {
        try
        {
            MessageDigest var2 = MessageDigest.getInstance(par0Str);
            byte[][] var3 = par1ArrayOfByte;
            int var4 = par1ArrayOfByte.length;

            for (int var5 = 0; var5 < var4; ++var5)
            {
                byte[] var6 = var3[var5];
                var2.update(var6);
            }

            return var2.digest();
        }
        catch (NoSuchAlgorithmException var7)
        {
            var7.printStackTrace();
            return null;
        }
    }
    
    public static SecretKey decryptSharedKey(PrivateKey par0PrivateKey, byte[] par1ArrayOfByte)
    {
        return new SecretKeySpec(decryptData(par0PrivateKey, par1ArrayOfByte), "AES");
    }
    
    public static byte[] decryptData(Key par0Key, byte[] par1ArrayOfByte)
    {
        return cipherOperation(2, par0Key, par1ArrayOfByte);
    }
    
    private static byte[] cipherOperation(int par0, Key par1Key, byte[] par2ArrayOfByte)
    {
        try
        {
            return createTheCipherInstance(par0, par1Key.getAlgorithm(), par1Key).doFinal(par2ArrayOfByte);
        }
        catch (IllegalBlockSizeException var4)
        {
            var4.printStackTrace();
        }
        catch (BadPaddingException var5)
        {
            var5.printStackTrace();
        }

        System.err.println("Cipher data failed!");
        return null;
    }
    
    private static Cipher createTheCipherInstance(int par0, String par1Str, Key par2Key)
    {
        try
        {
            Cipher var3 = Cipher.getInstance(par1Str);
            var3.init(par0, par2Key);
            return var3;
        }
        catch (InvalidKeyException var4)
        {
            var4.printStackTrace();
        }
        catch (NoSuchAlgorithmException var5)
        {
            var5.printStackTrace();
        }
        catch (NoSuchPaddingException var6)
        {
            var6.printStackTrace();
        }

        System.err.println("Cipher creation failed!");
        return null;
    }
}
