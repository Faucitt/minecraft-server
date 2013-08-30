package faucitt.terrain;

public class Chunk {

	public void generate(int chunkY) {
		if (chunkY == 0) {
			int y = 0;
			for (int x = 0; x < 16; x++) {
				for (int z = 0; z < 16; z++) {
					setBlockId(x, y, z, (byte) 1);
					setBlockSunlight(x, y+1, z, (byte) 15);
				}
			}
			setBlockId(0, 1, 0, (byte) 50);
		}
	}
	
	private byte[] blockIds = new byte[16*16*16];
	
	public byte getBlockId(int x, int y, int z) {
		return blockIds[((y*16*16)+(z*16)+x)];
	}
	
	public void setBlockId(int x, int y, int z, byte id) {
		blockIds[((y*16*16)+(z*16)+x)] = id;
	}
	
	public byte[] getBlockIds() {
		return blockIds;
	}
	
	private byte[] blockMetaData = new byte[16*16*8];
	
	public byte getBlockMetaData(int x, int y, int z) {
		if ((x&1) == 0) return (byte) ((blockMetaData[((y*16*8)+(z*8)+(x/2))]   ) &0x0F);
		                return (byte) ((blockMetaData[((y*16*8)+(z*8)+(x/2))]>>4) &0x0F);
	}
	
	public void setBlockMetaData(int x, int y, int z, byte data) {
		if ((x&1) == 0) {
			blockMetaData[((y*16*8)+(z*8)+(x/2))] &= 0xF0;
			blockMetaData[((y*16*8)+(z*8)+(x/2))] |= data&0x0F;
		} else {
			blockMetaData[((y*16*8)+(z*8)+(x/2))] &= 0x0F;
			blockMetaData[((y*16*8)+(z*8)+(x/2))] |= ((data&0x0F)<<4);
		}
	}
	
	public byte[] getBlockMetaData() {
		return blockMetaData;
	}

	private byte[] blockLight = new byte[16*16*8];
	
	public byte getBlockLight(int x, int y, int z) {
		if ((x&1) == 0) return (byte) ((blockLight[((y*16*8)+(z*8)+(x/2))]   ) &0x0F);
		                return (byte) ((blockLight[((y*16*8)+(z*8)+(x/2))]>>4) &0x0F);
	}
	
	public void setBlockLight(int x, int y, int z, byte light) {
		if ((x&1) == 0) {
			blockLight[((y*16*8)+(z*8)+(x/2))] &= 0xF0;
			blockLight[((y*16*8)+(z*8)+(x/2))] |= light&0x0F;
		} else {
			blockLight[((y*16*8)+(z*8)+(x/2))] &= 0x0F;
			blockLight[((y*16*8)+(z*8)+(x/2))] |= ((light&0x0F)<<4);
		}
	}
	
	public byte[] getBlockLight() {
		return blockLight;
	}
	
	private byte[] blockSunlight = new byte[16*16*8];
	
	public byte getBlockSunlight(int x, int y, int z) {
		if ((x&1) == 0) return (byte) ((blockSunlight[((y*16*8)+(z*8)+(x/2))]   ) &0xF);
		                return (byte) ((blockSunlight[((y*16*8)+(z*8)+(x/2))]>>4) &0xF);
	}
	
	public void setBlockSunlight(int x, int y, int z, byte light) {
		if ((x&1) == 0) {
			blockSunlight[((y*16*8)+(z*8)+(x/2))] &= 0xF0;
			blockSunlight[((y*16*8)+(z*8)+(x/2))] |= light&0x0F;
		} else {
			blockSunlight[((y*16*8)+(z*8)+(x/2))] &= 0x0F;
			blockSunlight[((y*16*8)+(z*8)+(x/2))] |= ((light&0x0F)<<4);
		}
	}
	
	public byte[] getBlockSunlight() {
		return blockSunlight;
	}
}
