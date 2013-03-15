package server;

public class Configuration {
	private int maxPlayers = 32768;
	private String motd = "Example MOTD.";
	
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
}
