package server.terrain;

public class Chunk {	
	public Chunk() {
		generate();
	}
	
	public void generate() {
		int y = 0;
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				setBlockId(x, y, z, (byte) 1);
			}
		}
	}
	
	private byte[] blockIds = new byte[16*16*16];
	
	public int getBlockId(int x, int y, int z) {
		return blockIds[((y*16*16)+(z*16)+x)];
	}
	
	public void setBlockId(int x, int y, int z, byte id) {
		blockIds[((y*16*16)+(z*16)+x)] = id;
	}
	
	public byte[] getBlockIds() {
		return blockIds;
	}
	
	private byte[] blockMetaData = new byte[16*16*16];
	
	public int getBlockMetaData(int x, int y, int z) {
		return blockMetaData[((y*16*16)+(z*16)+x)];
	}
	
	public byte[] getBlockMetaData() {
		return blockMetaData;
	}
	
	private byte[] blockLight = new byte[16*16*8];
	
	public int getBlockLight(int x, int y, int z) {
		if ((x&1) == 1) return (blockLight[((y*16*8)+(z*8)+(x/2))]&0xF);
		                return (blockLight[((y*16*8)+(z*8)+(x/2))]>>4 &0xF);
	}
	
	public byte[] getBlockLight() {
		return blockLight;
	}
	
	private byte[] blockSunlight = new byte[16*16*8];
	
	public int getBlockSunlight(int x, int y, int z) {
		if ((x&1) == 1) return (blockSunlight[((y*16*8)+(z*8)+(x/2))]&0xF);
		                return (blockSunlight[((y*16*8)+(z*8)+(x/2))]>>4 &0xF);
	}
	
	public byte[] getBlockSunlight() {
		return blockLight;
	}
}
