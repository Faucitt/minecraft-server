package faucitt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class Configuration extends Properties {
	
	private static final long serialVersionUID = 7336810514964014386L;
	private int maxPlayers = 25;
	private String motd = "Welcome to my server!";
	private int chunkRange = 0;
	private boolean isOnline = true;
	private int port = 25565;

	public Configuration() {
		initialize();
	}

	public void initialize() {
		if (exists()) {
			load();
			save();
		} else {
			save();
		}
	}

	public void load() {
		try {
			load(new FileInputStream("faucitt.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		prop();
		
		Server.logger.log("Loaded Configuration");
	}
	
	public void prop() {
		port = getInteger("port", 25565);
		maxPlayers = getInteger("max-players", 25);
		chunkRange = getInteger("chunk-range", 0);
		isOnline = getBool("online", true);
		motd = getString("motd", "Welcome to my Faucitt server!");
	}

	public void save() {
		try {
			store(new FileOutputStream("faucitt.properties"),
					"### Faucitt Configuration ###");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Server.logger.log("Saved Configuration");
	}
	
	public Boolean exists() {
		File conf = new File("faucitt.properties");
		if(conf.exists()) {
			return true;
		}
		return false;
	}
	
	public double getDouble(String key, double value) {
		if (containsKey(key)) {
			return Double.parseDouble(getProperty(key));
		}
		setProperty(key, String.valueOf(value));
		return value;
	}
	
	public int getInteger(String key, int value) {
		if (containsKey(key)) {
			return Integer.parseInt(getProperty(key));
		}
		setProperty(key, String.valueOf(value));
		return value;
	}
	
	public String getString(String key, String value) {
		if (containsKey(key)) {
			return getProperty(key);
		}
		setProperty(key, value);
		return value;
	}
	
	public Boolean getBool(String key, Boolean value) {
		if (containsKey(key)) {
			return Boolean.parseBoolean(getProperty(key));
		}
		setProperty(key, String.valueOf(value));
		return value;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
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

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
