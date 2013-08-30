package faucitt.model;

public abstract class Entity {
	public static int nextId;
	
	private double x, y, z, vx, vy, vz;
	private double lastX, lastY, lastZ;
	private int id;
	private float yaw, pitch;
	private float lastYaw, lastPitch;
	
	public Entity() {
		this.setId(nextId);
		nextId++;
		if (nextId > -100 && nextId < 0) throw new RuntimeException("Ran out of entity ID's.");
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public double getVX() {
		return vx;
	}

	public void setVX(double vx) {
		this.vx = vx;
	}

	public double getVY() {
		return vy;
	}

	public void setVY(double vy) {
		this.vy = vy;
	}

	public double getVZ() {
		return vz;
	}

	public void setVZ(double vz) {
		this.vz = vz;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public double getLastX() {
		return lastX;
	}

	public void setLastX(double lastX) {
		this.lastX = lastX;
	}

	public double getLastY() {
		return lastY;
	}

	public void setLastY(double lastY) {
		this.lastY = lastY;
	}

	public double getLastZ() {
		return lastZ;
	}

	public void setLastZ(double lastZ) {
		this.lastZ = lastZ;
	}

	public float getLastYaw() {
		return lastYaw;
	}

	public void setLastYaw(float lastYaw) {
		this.lastYaw = lastYaw;
	}

	public float getLastPitch() {
		return lastPitch;
	}

	public void setLastPitch(float lastPitch) {
		this.lastPitch = lastPitch;
	}
	
	
}
