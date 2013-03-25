package server.terrain;

public class World {
	private int dimension;
	private ChunkColumn[][] chunkColumns;
	private final int width, height, length;
	
	private int sizeX, sizeZ;
	
	public World(int dimension, int width, int length) {
		this.width = width*16;
		this.length = length*16;
		sizeX = width;
		sizeZ = length;
		this.height = 256;
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
		return chunk.getBlockId(x%16, y%16, z%16);
	}
	
	public byte getBlockMetaData(int x, int y, int z) {
		ChunkColumn column = chunkColumns[x/16][z/16];
		Chunk chunk = column.getChunk(y/16);
		return chunk.getBlockMetaData(x%16, y%16, z%16);
	}
	
	public byte getBlockLight(int x, int y, int z) {
		ChunkColumn column = chunkColumns[x/16][z/16];
		Chunk chunk = column.getChunk(y/16);
		return chunk.getBlockLight(x%16, y%16, z%16);
	}
	
	public byte getBlockSunlight(int x, int y, int z) {
		ChunkColumn column = chunkColumns[x/16][z/16];
		Chunk chunk = column.getChunk(y/16);
		return chunk.getBlockSunlight(x%16, y%16, z%16);
	}
	
	public void setBlockId(int x, int y, int z, byte id) {
		ChunkColumn column = chunkColumns[x/16][z/16];
		Chunk chunk = column.getChunk(y/16);
		chunk.setBlockId(x%16, y%16, z%16, id);
	}
	
	public void setBlockMetaData(int x, int y, int z, byte metaData) {
		ChunkColumn column = chunkColumns[x/16][z/16];
		Chunk chunk = column.getChunk(y/16);
		chunk.setBlockMetaData(x%16, y%16, z%16, metaData);
	}
	
	public void setBlockLight(int x, int y, int z, byte light) {
		ChunkColumn column = chunkColumns[x/16][z/16];
		Chunk chunk = column.getChunk(y/16);
		chunk.setBlockLight(x%16, y%16, z%16, light);
	}
	
	public void setBlockSunlight(int x, int y, int z, byte light) {
		ChunkColumn column = chunkColumns[x/16][z/16];
		Chunk chunk = column.getChunk(y/16);
		chunk.setBlockSunlight(x%16, y%16, z%16, light);
	}
	
	public void calculateLight() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				for (int z = 0; z < length; z++) {
					if (Block.getLightSources().containsKey(getBlockId(x, y, z))) {
						updateLight(x, y, z, Block.getLightSources().get(getBlockId(x, y, z)));
					}
				}
			}
		}
	}
	
	public void calculateSunlight() {
		for (int x = 0; x < width; x++) {
			for (int z = 0; z < length; z++) {
				for (int y = height-2; y >=0; y--) {
					updateSunlight(x, y+1, z, (byte) 15);
					if (!Block.getLightFilters().containsKey(getBlockId(x, y, z))) break;
					if (Block.getLightFilters().get(getBlockId(x, y, z)) != 0) break;
				}
			}
		}
	}
	
	public void updateLight(int x, int y, int z, byte level) {
		if (Block.getLightFilters().containsKey(getBlockId(x, y, z))) {
			byte reduction = Block.getLightFilters().get(getBlockId(x, y, z));
			byte light = (byte) (level - reduction);
			if (light > getBlockLight(x, y, z)) {
				setBlockLight(x, y, z, light);
				if (light > 1) {
					if (x > 0) updateLight(x-1, y, z, (byte) (light-1));
					if (x < (width-1)) updateLight(x+1, y, z, (byte) (light-1));
					if (y > 0) updateLight(x, y-1, z, (byte) (light-1));
					if (y < (height-1)) updateLight(x, y+1, z, (byte) (light-1));
					if (z > 0) updateLight(x, y, z-1, (byte) (light-1));
					if (z < (length-1)) updateLight(x, y, z+1, (byte) (light-1));
				}
			}
		}
	}
	
	public void updateSunlight(int x, int y, int z, byte level) {
		if (Block.getLightFilters().containsKey(getBlockId(x, y, z))) {
			byte reduction = Block.getLightFilters().get(getBlockId(x, y, z));
			byte light = (byte) (level - reduction);
			if (light > getBlockSunlight(x, y, z)) {
				setBlockSunlight(x, y, z, light);
				if (light > 1) {
					if (x > 0) updateSunlight(x-1, y, z, (byte) (light-1));
					if (x < (width-1)) updateSunlight(x+1, y, z, (byte) (light-1));
					if (y > 0) updateSunlight(x, y-1, z, (byte) (light-1));
					if (y < (height-1)) updateSunlight(x, y+1, z, (byte) (light-1));
					if (z > 0) updateSunlight(x, y, z-1, (byte) (light-1));
					if (z < (length-1)) updateSunlight(x, y, z+1, (byte) (light-1));
				}
			}
		}
	}

	public int getSizeX() {
		return sizeX;
	}

	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	public int getSizeZ() {
		return sizeZ;
	}

	public void setSizeZ(int sizeZ) {
		this.sizeZ = sizeZ;
	}
}
