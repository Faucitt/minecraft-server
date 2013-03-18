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
	
	public byte getBlockId(int x, int y, int z) {
		ChunkColumn column = chunkColumns[x/16][z/16];
		Chunk chunk = column.getChunk(y/16);
		return chunk.getBlockId(x&4, y&4, z&4);
	}
	
	public byte getBlockMetaData(int x, int y, int z) {
		ChunkColumn column = chunkColumns[x/16][z/16];
		Chunk chunk = column.getChunk(y/16);
		return chunk.getBlockMetaData(x&4, y&4, z&4);
	}
	
	public byte getBlockLight(int x, int y, int z) {
		ChunkColumn column = chunkColumns[x/16][z/16];
		Chunk chunk = column.getChunk(y/16);
		return chunk.getBlockLight(x&4, y&4, z&4);
	}
	
	public byte getBlockSunlight(int x, int y, int z) {
		ChunkColumn column = chunkColumns[x/16][z/16];
		Chunk chunk = column.getChunk(y/16);
		return chunk.getBlockSunlight(x&4, y&4, z&4);
	}
	
	public void setBlockId(int x, int y, int z, byte id) {
		ChunkColumn column = chunkColumns[x/16][z/16];
		Chunk chunk = column.getChunk(y/16);
		chunk.setBlockId(x&4, y&4, z&4, id);
	}
	
	public void setBlockMetaData(int x, int y, int z, byte metaData) {
		ChunkColumn column = chunkColumns[x/16][z/16];
		Chunk chunk = column.getChunk(y/16);
		chunk.setBlockMetaData(x&4, y&4, z&4, metaData);
	}
	
	public void setBlockLight(int x, int y, int z, byte light) {
		ChunkColumn column = chunkColumns[x/16][z/16];
		Chunk chunk = column.getChunk(y/16);
		chunk.setBlockLight(x&4, y&4, z&4, light);
	}
	
	public void setBlockSunlight(int x, int y, int z, byte light) {
		ChunkColumn column = chunkColumns[x/16][z/16];
		Chunk chunk = column.getChunk(y/16);
		chunk.setBlockSunlight(x&4, y&4, z&4, light);
	}
}
