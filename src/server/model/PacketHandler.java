package server.model;

import java.io.IOException;

import server.io.MCSocket;
import server.packet.*;

public class PacketHandler implements Runnable {
	private Player player;
	
	public PacketHandler(Player player) {
		setPlayer(player);
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void handlePacket(byte packetId, MCSocket socket) throws IOException {
		switch(packetId) {
		case (byte) 0x00:
			PingPacket pingPacket = PingPacket.read(socket);
			System.out.println(pingPacket);
			break;
		case (byte) 0x03:
			ChatPacket chatPacket = ChatPacket.read(socket);
			String chatMessage = chatPacket.getMessage();
			
			chatMessage = "<" + player.getUsername() + "> " + chatMessage;
			ChatPacket chatResponsePacket = new ChatPacket(chatMessage);
			for (Player p : player.getServer().getEntityHandler().getPlayers()) {
				p.pushPacket(chatResponsePacket);
			}
			
			System.out.println("<" + player.getUsername() + "> " + chatMessage);
			break;
		case (byte) 0x07:
			UseEntityPacket useEntityPacket = UseEntityPacket.read(socket);
			System.out.println(useEntityPacket);
			break;
		case (byte) 0x0A:
			FlyingPacket flyingPacket = FlyingPacket.read(socket);
			System.out.println(flyingPacket);
			break;
		case (byte) 0x0B:
			PositionPacket positionPacket = PositionPacket.read(socket);
			System.out.println(positionPacket);
			break;
		case (byte) 0x0C:
			LookPacket lookPacket = LookPacket.read(socket);
			System.out.println(lookPacket);
			break;
		case (byte) 0x0D:
			PositionLookPacket positionLookPacket = PositionLookPacket.read(socket);
			System.out.println(positionLookPacket);
			break;
		case (byte) 0x0E:
			DiggingPacket diggingPacket = DiggingPacket.read(socket);
			System.out.println(diggingPacket);
			break;
		case (byte) 0x12:
			AnimationPacket animationPacket = AnimationPacket.read(socket);
			System.out.println(animationPacket);
			break;
		case (byte) 0xCC:
			SettingsPacket settingsPacket = SettingsPacket.read(socket);
			System.out.println(settingsPacket);
			break;
		case (byte) 0xCD:
			StatusPacket statusPacket = StatusPacket.read(socket);
			System.out.println(statusPacket);
			break;
		default:
			System.out.println("Unhandled packet: 0x" + Integer.toHexString(packetId&0xFF));
			throw new IOException("Unhandled packet: 0x" + Integer.toHexString(packetId&0xFF));
		}
	}

	@Override
	public void run() {
		boolean keepRunning = true;
		while (keepRunning) {
			try {
				player.receivePacket();
			} catch (IOException e) {
				player.getServer().getEntityHandler().removePlayer(player);
				keepRunning = false;
			}
		}
		try { player.getSocket().close(); } catch (IOException e) {}
	}
}
