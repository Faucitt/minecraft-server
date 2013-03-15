package server.terrain;

public class Chunk {
	private int[][][] blocks = new int[16][16][16];
	
	public Chunk() {}
	
	public int getBlock(int x, int y, int z) {
		return blocks[x][y][z];
	}
}
