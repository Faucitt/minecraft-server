package faucitt.model;

import java.io.IOException;
import java.util.Iterator;

import faucitt.Server;
import faucitt.io.MCSocket;
import faucitt.io.packet.*;
import faucitt.io.packet.AnimationPacket.Animation;
import faucitt.logging.Logger;


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
			
			Server.logger.log(chatMessage);
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
			
			player.checkChunks();
			break;
			
		case (byte) 0x0C:
			LookPacket lookPacket = LookPacket.read(socket);
		
			player.setYaw((float) ((lookPacket.getYaw()*Math.PI*2/360)%(Math.PI*2)));
			player.setPitch((float) (lookPacket.getPitch()*Math.PI*2/360));
			break;
			
		case (byte) 0x0D:
			PositionLookPacket positionLookPacket = PositionLookPacket.read(socket);
		
			player.setX(positionLookPacket.getX());
			player.setY(positionLookPacket.getY());
			player.setZ(positionLookPacket.getZ());
			player.setYaw((float) ((positionLookPacket.getYaw()*Math.PI*2/360)%(Math.PI*2)));
			player.setPitch((float) (positionLookPacket.getPitch()*Math.PI*2/360));
			
			player.checkChunks();
			break;
			
		case (byte) 0x0E:
			DiggingPacket diggingPacket = DiggingPacket.read(socket);
			break;
			
		case (byte) 0x12:
			AnimationPacket animationPacket = AnimationPacket.read(socket);
		
			if (animationPacket.getAnimation() == Animation.SWING) {
				AnimationPacket swingPacket = new AnimationPacket();
				swingPacket.setAnimation(Animation.SWING);
				swingPacket.setEntityId(player.getId());
				
				Iterator<Player> iterator = player.getServer().getEntityHandler().getPlayerIterator();
				while (iterator.hasNext()) {
					Player player = iterator.next();
					if (player == null) continue;
					if (player == this.player) continue;
					
					if (player.hasPlayer(this.player)) player.pushPacket(swingPacket);
				}
			}
			break;
			
		case (byte) 0x13:
			EntityActionPacket entityActionPacket = EntityActionPacket.read(socket);
			break;
			
		case (byte) 0xCC:
			SettingsPacket settingsPacket = SettingsPacket.read(socket);
			break;
			
		case (byte) 0xCD:
			StatusPacket statusPacket = StatusPacket.read(socket);
			break;
			
		default:
			Server.logger.log("Unhandled packet recieved: 0x" + Integer.toHexString(packetId&0xFF));
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
