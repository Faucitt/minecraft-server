package server.model;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import server.Configuration;
import server.Server;
import server.io.Cryptography;
import server.io.MCSocket;
import server.packet.DisconnectPacket;
import server.packet.EncryptionRequestPacket;
import server.packet.EncryptionResponsePacket;
import server.packet.HandshakePacket;
import server.packet.Packet;
import server.util.Encode;

public class Player extends Entity implements Runnable {
	private MCSocket socket;
	private String username;
	private Server server;
	private byte[] verificationToken;
	private KeyPair keyPair;
	private Queue<Packet> packetQueue = new ArrayBlockingQueue<Packet>(4096);
	
	public Player(MCSocket socket, Server server) throws NoSuchAlgorithmException {
		setSocket(socket);
		setServer(server);
		
		Random rng = new Random();
		verificationToken = new byte[4];
		rng.nextBytes(verificationToken);
		
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(1024);
		keyPair = kpg.generateKeyPair();
	}

	public MCSocket getSocket() {
		return socket;
	}

	public void setSocket(MCSocket socket) {
		this.socket = socket;
	}
	
	public void pushPacket(Packet packet) {
		packetQueue.add(packet);
	}
	
	public Packet popPacket() {
		return packetQueue.poll();
	}
	
	public boolean sendPacket() throws IOException {
		Packet packet = popPacket();
		if (packet != null) {
			socket.writeByte(packet.getId());
			packet.write(socket);
			return true;
		}
		return false;
	}
	
	public boolean receivePacket() throws IOException {
		return false;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		boolean keepRunning = true;
		
		//Login sequence.
		try {
			byte mode = socket.readByte();
			switch(mode) {
			case (byte) 0xFE:
				socket.readByte();//unused
			
				Configuration config = server.getConfiguration();
				DisconnectPacket packet = new DisconnectPacket("§1" + Encode.character(0) + 
						"60" + Encode.character(0) + 
						"1.5" + Encode.character(0) + 
						config.getMotd() + Encode.character(0) + 
						Integer.toString(server.getEntityHandler().getPlayerCount()-1) + Encode.character(0) + 
						Integer.toString(config.getMaxPlayers()));
				socket.writeByte(packet.getId());
				packet.write(socket);
				
				socket.close();
				keepRunning = false;
				break;
			case (byte) 0x02:
			default:
				System.out.println("Login request.");
				
				HandshakePacket handshake = HandshakePacket.read(socket);
				setUsername(handshake.getUsername());
				
				EncryptionRequestPacket encryptRequest = new EncryptionRequestPacket(server.getServerId(), keyPair.getPublic().getEncoded(), verificationToken);
				socket.writeByte(encryptRequest.getId());
				encryptRequest.write(socket);
				
				byte responseId = socket.readByte();
				if (responseId != 0xFC) throw new IOException("Unexpected packet. Expected 0xFC - EncryptionResponse");
				EncryptionResponsePacket encryptResponse = EncryptionResponsePacket.read(socket);
				SecretKey sharedKey = Cryptography.decryptSharedKey(keyPair.getPrivate(), encryptResponse.getSharedSecret());
				
				//TODO: Check verification token is valid, prevents MITM attacks.
				EncryptionResponsePacket emptyResponse = new EncryptionResponsePacket(new byte[] {}, new byte[] {});
				socket.writeByte(emptyResponse.getId());
				emptyResponse.write(socket);
				
				socket.enableEncryption(sharedKey);
				break;
			}
		} catch (IOException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException e) {
			server.getEntityHandler().removePlayer(this);
		}
		
		//Packet reading and writing.
		while (keepRunning) {
			try {
				if (!(sendPacket() || receivePacket())) Thread.currentThread().sleep(10);
			} catch (IOException | InterruptedException e) {
				server.getEntityHandler().removePlayer(this);
				keepRunning = false;
			}
		}
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}
}
