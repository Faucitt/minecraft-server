package server.model;

import java.io.IOException;

public class PacketReader implements Runnable {
	private Player player;
	
	public PacketReader(Player player) {
		setPlayer(player);
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
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
