package server.model;

import java.io.IOException;

import server.io.MCSocket;
import server.io.packet.*;
import server.logging.Logger;

public class PacketHandler implements Runnable {
	private static final Logger logger = Logger.getLogger(PacketHandler.class.getName());
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
	
	@SuppressWarnings("unused")
	public void handlePacket(byte packetId, MCSocket socket) throws IOException {
		switch(packetId) {
		case (byte) 0x00:
			PingPacket pingPacket = PingPacket.read(socket);
			break;
			
		case (byte) 0x03:
			ChatPacket chatPacket = ChatPacket.read(socket);
			String chatMessage = chatPacket.getMessage();
			
			chatMessage = "<" + player.getUsername() + "> " + chatMessage;
			ChatPacket chatResponsePacket = new ChatPacket(chatMessage);
			for (Player p : player.getServer().getEntityHandler().getPlayers()) {
				p.pushPacket(chatResponsePacket);
			}
			
			logger.write(chatMessage);
			break;
			
		case (byte) 0x07:
			UseEntityPacket useEntityPacket = UseEntityPacket.read(socket);
			break;
			
		case (byte) 0x0A:
			FlyingPacket flyingPacket = FlyingPacket.read(socket);
			break;
			
		case (byte) 0x0B:
			PositionPacket positionPacket = PositionPacket.read(socket);
			player.setX(positionPacket.getX());
			player.setY(positionPacket.getY());
			player.setZ(positionPacket.getZ());
			break;
			
		case (byte) 0x0C:
			LookPacket lookPacket = LookPacket.read(socket);
			player.setYaw((float) (((lookPacket.getYaw()*Math.PI*2/360)-(Math.PI/2))%(Math.PI*2)));
			player.setPitch((float) ((lookPacket.getPitch()*Math.PI*2/360)));
			break;
			
		case (byte) 0x0D:
			PositionLookPacket positionLookPacket = PositionLookPacket.read(socket);
			player.setX(positionLookPacket.getX());
			player.setY(positionLookPacket.getY());
			player.setZ(positionLookPacket.getZ());
			player.setYaw((float) (((positionLookPacket.getYaw()*Math.PI*2/360)-(Math.PI/2))%(Math.PI*2)));
			player.setPitch((float) ((positionLookPacket.getPitch()*Math.PI*2/360)));
			break;
			
		case (byte) 0x0E:
			DiggingPacket diggingPacket = DiggingPacket.read(socket);
			break;
			
		case (byte) 0x12:
			AnimationPacket animationPacket = AnimationPacket.read(socket);
			break;
			
		case (byte) 0x13:
			ActionPacket actionPacket = ActionPacket.read(socket);
			break;
			
		case (byte) 0xCC:
			SettingsPacket settingsPacket = SettingsPacket.read(socket);
			break;
			
		case (byte) 0xCD:
			StatusPacket statusPacket = StatusPacket.read(socket);
			break;
			
		default:
			logger.write("Unhandled packet: 0x" + Integer.toHexString(packetId&0xFF));
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
