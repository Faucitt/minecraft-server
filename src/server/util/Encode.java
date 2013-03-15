package server.util;

public class Encode {
	public static String character(int i) {
		return new String(new byte[] {(byte) i});
	}
}
