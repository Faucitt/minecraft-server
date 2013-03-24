package server;

import java.io.IOException;
import java.util.Iterator;

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
