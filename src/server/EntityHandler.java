package server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import server.model.Entity;
import server.model.Player;

public class EntityHandler {
	public static List<Entity> entities = new ArrayList<Entity>();
	public static List<Player> players = new ArrayList<Player>();
	
	public List<Entity> getEntities() {
		return entities;
	}
	
	public Iterator<Entity> getEntityIterator() {
		return entities.iterator();
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
	}
	
	public void removeEntity(Entity e) {
		entities.remove(e);
	}
	
	public void addPlayer(Player p) {
		entities.add((Entity) p);
		players.add(p);
	}
	
	public void removePlayer(Player p) {
		entities.remove((Entity) p);
		players.remove(p);
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
