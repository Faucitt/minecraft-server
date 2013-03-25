package server.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
import server.io.packet.ChunkColumnPacket;
import server.io.packet.DestroyEntitiesPacket;
import server.io.packet.DisconnectPacket;
import server.io.packet.EncryptionRequestPacket;
import server.io.packet.EncryptionResponsePacket;
import server.io.packet.FlyingPacket;
import server.io.packet.HandshakePacket;
import server.io.packet.LoginPacket;
import server.io.packet.Packet;
import server.io.packet.SpawnPlayerPacket;
import server.io.packet.TimePacket;
import server.util.Encode;

public class Player extends Entity implements Runnable {
	
	private MCSocket socket;
	
	private String username;
	
	private Server server;
	
	private byte[] verificationToken;
	private KeyPair keyPair;
	
	private PacketHandler packetHandler;
	private Queue<Packet> packetQueue = new ArrayBlockingQueue<Packet>(4096);
	
	private List<Player> players = new ArrayList<>();
	
	public Player(MCSocket socket, Server server) throws NoSuchAlgorithmException {
		super();
		
		setSocket(socket);
		setServer(server);
		
		Random rng = new Random();
		verificationToken = new byte[4];
		rng.nextBytes(verificationToken);
		
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(1024);
		keyPair = keyPairGenerator.generateKeyPair();
	}
	
	public boolean hasPlayer(Player player) {
		return players.contains(player);
	}
	
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	public void checkPlayers() throws IOException {
		Iterator<Player> iterator = server.getEntityHandler().getPlayerIterator();
		
		while (iterator.hasNext()) {
			Player player = iterator.next();
			if (player == null) continue;
			if (player == this) continue;
			
			if (player.getEuclideanDistance(this) <= 168) {
				if (!hasPlayer(player)) {
					players.add(player);
					
					SpawnPlayerPacket spawnPlayerPacket = new SpawnPlayerPacket();
					spawnPlayerPacket.setName(player.getUsername());
					spawnPlayerPacket.setEntityId(player.getId());
					spawnPlayerPacket.setX(player.getX());
					spawnPlayerPacket.setY(player.getY());
					spawnPlayerPacket.setZ(player.getZ());
					spawnPlayerPacket.setYaw(player.getYaw());
					spawnPlayerPacket.setPitch(player.getPitch());
					
					pushPacket(spawnPlayerPacket);
				}
			} else {
				if (hasPlayer(player)) {
					System.out.println("REMOVE " + player.getUsername());
					players.remove(player);
					
					DestroyEntitiesPacket destroyPacket = new DestroyEntitiesPacket();
					destroyPacket.setEntityIds(new int[] {player.getId()});
					
					pushPacket(destroyPacket);
				}
			}
		}
	}

	private double getEuclideanDistance(Player player) {
		return Math.sqrt(((this.getX()-player.getX())*(this.getX()-player.getX())) + ((this.getZ()-player.getZ())*(this.getZ()-player.getZ())));
	}
	
	//private double getDistance(Player player) {
	//	return Math.sqrt(((this.getX()-player.getX())*(this.getX()-player.getX())) + ((this.getZ()-player.getZ())*(this.getZ()-player.getZ())) + ((this.getY()-player.getY())*(this.getY()-player.getY())));
	//}

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
	
	public void receivePacket() throws IOException {
		byte packetId = socket.readByte();
		packetHandler.handlePacket(packetId, socket);
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
						Integer.toString(server.getEntityHandler().getPlayerCount()) + Encode.character(0) + 
						Integer.toString(config.getMaxPlayers()));
				socket.writeByte(packet.getId());
				packet.write(socket);
				
				socket.close();
				
				server.getEntityHandler().removePlayer(this);
				keepRunning = false;
				break;
			case (byte) 0x02:
			default:
				HandshakePacket handshake = HandshakePacket.read(socket);
				setUsername(handshake.getUsername());
				
				EncryptionRequestPacket encryptRequest = new EncryptionRequestPacket(server.getServerId(), keyPair.getPublic().getEncoded(), verificationToken);
				socket.writeByte(encryptRequest.getId());
				encryptRequest.write(socket);
				
				byte responseId = socket.readByte();
				if (responseId != ((byte) 0xFC)) throw new IOException("Unexpected packet. Expected 0xFC - EncryptionResponse");
				EncryptionResponsePacket encryptResponse = EncryptionResponsePacket.read(socket);
				SecretKey sharedKey = Cryptography.decryptSharedKey(keyPair.getPrivate(), encryptResponse.getSharedSecret());
				
				byte[] idBytes = Cryptography.getServerIdHash(server.getServerId(), keyPair.getPublic(), sharedKey);
				String id = (new BigInteger(idBytes)).toString(16);
	            URL url = new URL("http://session.minecraft.net/game/checkserver.jsp?user=" + URLEncoder.encode(username, "UTF-8") + "&serverId=" + URLEncoder.encode(id, "UTF-8"));
	            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
	            String response = reader.readLine();
	            reader.close();
	            if (!response.equals("YES")) throw new IOException("Player not authenticated with minecraft.net.");
				
				//TODO: Check verification token is valid, prevents MITM attacks.
				EncryptionResponsePacket emptyResponse = new EncryptionResponsePacket(new byte[] {}, new byte[] {});
				socket.writeByte(emptyResponse.getId());
				emptyResponse.write(socket);
				
				socket.enableEncryption(sharedKey);
				
				LoginPacket loginPacket = new LoginPacket(getId());
				loginPacket.setLevelType("default");
				loginPacket.setGameMode((byte) 0);
				loginPacket.setDimension((byte) 0);
				loginPacket.setDifficulty((byte) 0);
				loginPacket.setMaxPlayers((byte) 32);
				socket.writeByte(loginPacket.getId());
				loginPacket.write(socket);
				
				
				ChunkColumnPacket chunkPacket = new ChunkColumnPacket();
				chunkPacket.setWorldAndChunkColumn(server.getWorlds().get(0), 0, 0);
				socket.writeByte(chunkPacket.getId());
				chunkPacket.write(socket);
				
				TimePacket timePacket = new TimePacket(18000);
				socket.writeByte(timePacket.getId());
				timePacket.write(socket);
				
				FlyingPacket flyingPacket = new FlyingPacket(true);
				socket.writeByte(flyingPacket.getId());
				flyingPacket.write(socket);
				
				server.getEntityHandler().addPlayer(this);
				break;
			}
		} catch (IOException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException e) {
			try { socket.close(); } catch (IOException ex) {}
			server.getEntityHandler().removePlayer(this);
		}
		
		//Packet reading.
		if (keepRunning) {
			packetHandler = new PacketHandler(this);
			Thread thread = new Thread(packetHandler);
			thread.start();
		}
		
		//Packet writing.
		while (keepRunning) {
			try {
				if (!(sendPacket())) Thread.currentThread().sleep(10);
			} catch (IOException | InterruptedException e) {
				server.getEntityHandler().removePlayer(this);
				keepRunning = false;
			}
		}
		try { socket.close(); } catch (IOException e) {}
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}
}
