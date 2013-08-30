package faucitt.io.packet;

import java.io.IOException;

import faucitt.io.MCSocket;
import faucitt.terrain.Chunk;
import faucitt.terrain.ChunkColumn;
import faucitt.terrain.World;


public class ChunkColumnPacket extends Packet {

	private ChunkColumn chunks;
	private World world;
	
	private int x, z;
	
	public ChunkColumnPacket() {
		super((byte) 0x33);
	}

	@Override
	public void write(MCSocket socket) throws IOException {
		boolean continuous = isContinuous();
		socket.writeInt(x);
		socket.writeInt(z);
		socket.writeByte((byte) (continuous ? 1 : 0));
		socket.writeShort(getPrimaryBitmap());
		socket.writeShort(getAddBitmap());
		socket.writeBigByteArray(world.getCompressedChunk(x, z));
	}
	
	public short getAddBitmap() {
		return 0;
	}
	
	public short getPrimaryBitmap() {
		short bitmap = 0;
		for (int y = 0; y < 16; y++) {
			if (chunks.getChunk(y) != null) {
				bitmap |= 1<<y;
			}
		}
		return bitmap;
	}

	public Chunk[] getChunks() {
		return chunks.getChunks();
	}

	public void setChunks(Chunk[] chunks) {
		this.chunks.setChunks(chunks);
	}
	
	public boolean isContinuous() {
		for (Chunk chunk : chunks.getChunks()) {
			if (chunk == null) return false;
		}
		return true;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
	
	public void setWorldAndChunkColumn(World world, int x, int z) {
		this.world = world;
		this.x = x;
		this.z = z;
		chunks = world.getChunkColumn(x, z);
	}

}
