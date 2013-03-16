package server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import server.model.Entity;
import server.model.Player;
import server.packet.ChatPacket;
import server.packet.Packet;

public class EntityHandler {
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
		entities.add((Entity) p);
		players.add(p);
		
		if (p.getUsername() == null) return;
		ChatPacket packet = new ChatPacket("§e" + p.getUsername() + " has joined.");
		for (Player player : players) {
			if (player != p) player.pushPacket((Packet) packet);
		}
	}
	
	public void removePlayer(Player p) {
		if (p == null) return;
		entities.remove((Entity) p);
		if (players.remove(p)) {
			if (p.getUsername() == null) return;
			ChatPacket packet = new ChatPacket("§e" + p.getUsername() + " has left.");
			for (Player player : players) {
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
