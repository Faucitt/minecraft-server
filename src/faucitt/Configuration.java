package faucitt;

public class Configuration {
	private int maxPlayers = 25;
	private String motd = "Welcome to my faucitt!";
	private int chunkRange = 0;
	private boolean isOnline = true;
	
	public int getMaxPlayers() {
		return maxPlayers;
	}
	
	public void setMaxPlayers(int max) {
		this.maxPlayers = max;
	}

	public String getMotd() {
		return motd;
	}

	public void setMotd(String motd) {
		this.motd = motd;
	}

	public int getChunkRange() {
		return chunkRange;
	}

	public void setChunkRange(int chunkRange) {
		this.chunkRange = chunkRange;
	}

	public boolean isOnline() {
		return isOnline;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}
}
