package server;

import java.io.IOException;
import java.util.Iterator;

import server.io.packet.EntityLookPacket;
import server.io.packet.EntityMoveLookPacket;
import server.io.packet.EntityMovePacket;
import server.io.packet.EntityTeleportPacket;
import server.io.packet.HeadYawPacket;
import server.io.packet.Packet;
import server.io.packet.PingPacket;
import server.model.Player;

public class TickHandler implements Runnable {

	private Server server;
	
	public TickHandler(Server server) {
		setServer(server);
	}
	
	@Override
	public void run() {
		long tick = 0;
		while (true) {
			long time = System.nanoTime();
			
			/* Per-tick calculations. */
			
			//Send ping packets to every player every 20 ticks (1 second).
			if (tick%20 == 0) {
				PingPacket pingPacket = new PingPacket();
				Iterator<Player> iterator = server.getEntityHandler().getPlayerIterator();
				while (iterator.hasNext()) {
					Player player = iterator.next();
					if (player == null) continue;
					player.pushPacket(pingPacket);
					try {
						player.checkPlayers();
					} catch (IOException e) {
						//TODO Panic!
						e.printStackTrace();
					}
				}
			}
			
			// Send movement packets to other nearby players 4 times per second.
			if (tick%5 == 0) {
				Iterator<Player> iterator = server.getEntityHandler().getPlayerIterator();
				while (iterator.hasNext()) {
					Player player = iterator.next();
					if (player == null) continue;
					
					Packet sendPacket = null;
					
					HeadYawPacket yawPacket = new HeadYawPacket();
					yawPacket.setEntityId(player.getId());
					yawPacket.setYaw(player.getYaw());
					
					if (tick%100 == 0) {
						//Send teleport packet.
						EntityTeleportPacket packet = new EntityTeleportPacket();
						
						packet.setEntityId(player.getId());
						packet.setX((int) (player.getX()*32));
						packet.setY((int) (player.getY()*32));
						packet.setZ((int) (player.getZ()*32));
						
						player.setLastX(player.getX());
						player.setLastY(player.getY());
						player.setLastZ(player.getZ());
						player.setLastYaw(player.getYaw());
						player.setLastPitch(player.getPitch());
						
						sendPacket = (Packet) packet;
					} else if (player.getYaw() != player.getLastYaw() || player.getPitch() != player.getLastPitch()) {
						if (player.getX() != player.getLastX() || player.getY() != player.getLastY() || player.getZ() != player.getLastZ()) {
							int dx = (int) ((player.getX()-player.getLastX())*32);
							int dy = (byte) ((player.getY()-player.getLastY())*32);
							int dz = (byte) ((player.getZ()-player.getLastZ())*32);
							if (dx > 127 || dx < -128 || dy > 127 || dy < -128 || dz > 127 || dz < -128) {
								//Player moved far, send teleport packet.
								EntityTeleportPacket packet = new EntityTeleportPacket();
								
								packet.setEntityId(player.getId());
								packet.setX((int) (player.getX()*32));
								packet.setY((int) (player.getY()*32));
								packet.setZ((int) (player.getZ()*32));
								
								player.setLastX(player.getX());
								player.setLastY(player.getY());
								player.setLastZ(player.getZ());
								player.setLastYaw(player.getYaw());
								player.setLastPitch(player.getPitch());
								
								sendPacket = (Packet) packet;
							} else {
								//Player moved normally, send movement packet.
								EntityMoveLookPacket packet = new EntityMoveLookPacket();
								
								packet.setDx((byte) dx);
								packet.setDy((byte) dy);
								packet.setDz((byte) dz);
								packet.setYaw(player.getYaw());
								packet.setPitch(player.getPitch());
								packet.setEntityId(player.getId());
								
								player.setLastX(player.getX());
								player.setLastY(player.getY());
								player.setLastZ(player.getZ());
								player.setLastYaw(player.getYaw());
								player.setLastPitch(player.getPitch());
								
								sendPacket = (Packet) packet;
							}
						} else {
							EntityLookPacket packet = new EntityLookPacket();
							
							packet.setEntityId(player.getId());
							packet.setYaw(player.getYaw());
							packet.setPitch(player.getPitch());
							
							player.setLastYaw(player.getYaw());
							player.setLastPitch(player.getPitch());
							
							sendPacket = (Packet) packet;
						}
					} else {
						if (player.getX() != player.getLastX() || player.getY() != player.getLastY() || player.getZ() != player.getLastZ()) {
							int dx = (int) ((player.getX()-player.getLastX())*32);
							int dy = (byte) ((player.getY()-player.getLastY())*32);
							int dz = (byte) ((player.getZ()-player.getLastZ())*32);
							if (dx > 127 || dx < -128 || dy > 127 || dy < -128 || dz > 127 || dz < -128) {
								//Player moved far, send teleport packet.
								EntityTeleportPacket packet = new EntityTeleportPacket();
								
								packet.setEntityId(player.getId());
								packet.setX((int) (player.getX()*32));
								packet.setY((int) (player.getY()*32));
								packet.setZ((int) (player.getZ()*32));
								
								player.setLastX(player.getX());
								player.setLastY(player.getY());
								player.setLastZ(player.getZ());
								player.setLastYaw(player.getYaw());
								
								player.setLastPitch(player.getPitch());
								
								sendPacket = (Packet) packet;
							} else {
								//Player moved normally, send movement packet.
								EntityMovePacket packet = new EntityMovePacket();
								
								packet.setDx((byte) dx);
								packet.setDy((byte) dy);
								packet.setDz((byte) dz);
								packet.setEntityId(player.getId());
								
								player.setLastX(player.getX());
								player.setLastY(player.getY());
								player.setLastZ(player.getZ());
								
								sendPacket = (Packet) packet;
							}
						}
					}
					
					if (sendPacket != null) {
						Iterator<Player> iterator2 = server.getEntityHandler().getPlayerIterator();
						
						while (iterator2.hasNext()) {
							Player player2 = iterator2.next();
							
							if (player2 == null) continue;
							if (player == player2) continue;
							
							if (player2.hasPlayer(player)) {
								player2.pushPacket(sendPacket);
								player2.pushPacket((Packet) yawPacket);
							}
						}
					}
				}
			}
			
			/* End of per-tick calculations. */
			
			long difference = System.nanoTime()-time;
			if (difference > 0) {
				Thread.currentThread();
				try {
					Thread.sleep((50000000-difference)/1000000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			tick++;
			server.incrementTime();
		}
	}



	public Server getServer() {
		return server;
	}



	public void setServer(Server server) {
		this.server = server;
	}

}
