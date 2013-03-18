package server.terrain;

public class World {
	private int dimension;
	private ChunkColumn[][] chunkColumns;
	
	public World(int dimension, int width, int length) {
		setDimension(dimension);
		setChunkColumns(new ChunkColumn[width][length]);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < width; y++) {
				chunkColumns[x][y] = new ChunkColumn();
			}
		}
	}
	
	public byte[] getCompressedChunk(int x, int z) {
		return getChunkColumn(x, z).getCompressedData();
	}
	
	public int getDimension() {
		return dimension;
	}
	
	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	public ChunkColumn[][] getChunkColumns() {
		return chunkColumns;
	}

	public void setChunkColumns(ChunkColumn[][] chunkColumns) {
		this.chunkColumns = chunkColumns;
	}
	
	public ChunkColumn getChunkColumn(int x, int z) {
		return chunkColumns[x][z];
	}
}
