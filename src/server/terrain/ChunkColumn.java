package server.terrain;

import java.util.Arrays;
import java.util.zip.Deflater;

public class ChunkColumn {
	private Chunk[] chunks = new Chunk[16];
	byte[][] biomes = new byte[16][16];
	
	public ChunkColumn() {
		for (int y = 0; y < 16; y++) {
			chunks[y] = new Chunk();
			chunks[y].generate(y);
		}
	}
	
	private byte[] compressedData;
	private boolean compressed = false;
	
	public byte[] getCompressedData() {
		if (compressed) return compressedData;
		byte[] buffer = new byte[(16*16*16*16*3)+(16*16)];
		int pos = 0;
		
		for (int y = 0; y < 16; y++) {
			System.arraycopy(getChunk(y).getBlockIds(), 0, buffer, pos, 16*16*16);
			pos += 16*16*16;
		}
		
		for (int y = 0; y < 16; y++) {
			System.arraycopy(getChunk(y).getBlockMetaData(), 0, buffer, pos, 16*16*8);
			pos += 16*16*8;
		}
		
		for (int y = 0; y < 16; y++) {
			System.arraycopy(getChunk(y).getBlockLight(), 0, buffer, pos, 16*16*8);
			pos += 16*16*8;
		}
		
		for (int y = 0; y < 16; y++) {
			System.arraycopy(getChunk(y).getBlockSunlight(), 0, buffer, pos, 16*16*8);
			pos += 16*16*8;
		}
		
		for (int sx = 0; sx < 16; sx++) {
			for (int sz = 0; sz < 16; sz++) {
				buffer[pos] = biomes[sx][sz];
				pos++;
			}
		}
		
		byte[] compressedData = new byte[(16*16*16*16*3)+(16*16)];
		Deflater deflater = new Deflater();
		deflater.setInput(buffer, 0, (16*16*16*16*3)+(16*16));
		deflater.finish();
		int length = deflater.deflate(compressedData);
		compressed = true;
		this.compressedData = Arrays.copyOf(compressedData, length);
		return this.compressedData;
	}
	
	public Chunk getChunk(int y) {
		return chunks[y];
	}

	public Chunk[] getChunks() {
		return chunks;
	}

	public void setChunks(Chunk[] chunks) {
		this.chunks = chunks;
	}
}
