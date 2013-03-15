package server.model;

public abstract class Entity {
	public static int nextId;
	
	private double x, y, z, vx, vy, vz;
	private int id;
	
	public Entity() {
		this.setId(nextId);
		nextId++;
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
	
	
}
