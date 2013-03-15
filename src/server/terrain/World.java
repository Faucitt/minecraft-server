package server.terrain;

public class World {
	private int dimension;
	
	Chunk[][][] chunks;
	
	public World(int dimension, int width, int length, int height) {
		chunks = new Chunk[width/16][length/16][height/16];
		setDimension(dimension);
	}
	
	public int getDimension() {
		return dimension;
	}
	
	public void setDimension(int dimension) {
		this.dimension = dimension;
	}
	
	public int getBlock(int x, int y, int z) {
		return chunks[x/16][y/16][z/16].getBlock(x%16, y%16, z%16);
	}
	
	public Chunk getChunk(int x, int y, int z) {
		return chunks[x][y][z];
	}
}
