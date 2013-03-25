package server;

public class Configuration {
	private int maxPlayers = 32768;
	private String motd = "Example MOTD.";
	private int chunkRange = 0;
	
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
}
