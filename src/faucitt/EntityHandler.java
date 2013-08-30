package faucitt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import faucitt.io.packet.ChatPacket;
import faucitt.io.packet.DestroyEntitiesPacket;
import faucitt.io.packet.Packet;
import faucitt.logging.Logger;
import faucitt.model.Entity;
import faucitt.model.Player;


public class EntityHandler {
	private static final Logger logger = Logger.getLogger(EntityHandler.class.getName());
	
	public List<Entity> entities = new ArrayList<>();
	public List<Player> players = new ArrayList<>();
	
	public List<Entity> getEntities() {
		return entities;
	}
	
	public Iterator<Entity> getEntityIterator() {
		return entities.iterator();
	}
	
	public void addEntity(Entity e) {
		if (e == null) return;
		entities.add(e);
	}
	
	public void removeEntity(Entity e) {
		if (e == null) return;
		entities.remove(e);
	}
	
	public void addPlayer(Player p) {
		if (p == null) return;

		players.add(p);
		
		if (p.getUsername() == null) return;
		
		String joinMessage = p.getUsername() + " has joined.";
		ChatPacket packet = new ChatPacket("§e" + joinMessage);
		
		logger.log(joinMessage);
		
		Iterator<Player> iterator = getPlayerIterator();
		
		while (iterator.hasNext()) {
			Player player = iterator.next();
			if (player == null) continue;
			
			if (player != p) player.pushPacket((Packet) packet);
		}
	}
	
	public void removePlayer(Player p) {
		if (p == null) return;

		if (players.remove(p)) {
			if (p.getUsername() == null) return;
			
			String leaveMessage = p.getUsername() + " has left.";
			ChatPacket packet = new ChatPacket("§e" + leaveMessage);
			
			DestroyEntitiesPacket destroyPacket = new DestroyEntitiesPacket();
			destroyPacket.setEntityIds(new int[] {p.getId()});
			
			logger.log(leaveMessage);
			
			Iterator<Player> iterator = getPlayerIterator();
			
			while (iterator.hasNext()) {
				Player player = iterator.next();
				if (player == null) continue;
				
				if (player.hasPlayer(p)) player.pushPacket(destroyPacket);
				if (player != p) player.pushPacket((Packet) packet);
			}
		}
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public Iterator<Player> getPlayerIterator() {
		return players.iterator();
	}
	
	public int getPlayerCount() {
		return players.size();
	}
	
	public int getEntityCount() {
		return entities.size();
	}
}
