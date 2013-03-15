package server.terrain;

public class Block {

	public static int pack(byte id, byte meta) {
		return (id<<24 | meta<<16);
	}
	
	public static byte getId(int block) {
		return (byte) (block>>24);
	}
	
	public static byte getMeta(int block) {
		return (byte) ((block>>16)&0xFF);
	}
	
}
