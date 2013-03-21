package server.terrain;

import java.util.HashMap;
import java.util.Map;

public class Block {
	private static final Map<Byte, Byte> lightSources = new HashMap<>();
	private static final Map<Byte, Byte> lightFilters = new HashMap<>();
	
	public static Map<Byte, Byte> getLightFilters() {
		return lightFilters;
	}

	public static Map<Byte, Byte> getLightSources() {
		return lightSources;
	}
	
	static {
		lightSources.put((byte) 138, (byte) 15);
		lightSources.put((byte) 119, (byte) 15);
		lightSources.put((byte) 51, (byte) 15);
		lightSources.put((byte) 89, (byte) 15);
		lightSources.put((byte) 91, (byte) 15);
		lightSources.put((byte) 10, (byte) 15);
		lightSources.put((byte) 11, (byte) 15);
		lightSources.put((byte) 124, (byte) 15);
		lightSources.put((byte) 50, (byte) 14);
		lightSources.put((byte) 62, (byte) 13);
		lightSources.put((byte) 90, (byte) 11);
		lightSources.put((byte) 94, (byte) 9);
		lightSources.put((byte) 74, (byte) 9);
		lightSources.put((byte) 130, (byte) 7);
		lightSources.put((byte) 76, (byte) 7);
		lightSources.put((byte) 117, (byte) 1);
		lightSources.put((byte) 39, (byte) 1);
		lightSources.put((byte) 122, (byte) 1);
		lightSources.put((byte) 120, (byte) 1);
		
		lightFilters.put((byte) 0, (byte) 0);//Air
		
		lightFilters.put((byte) 29, (byte) 5);
		lightFilters.put((byte) 33, (byte) 5);
		lightFilters.put((byte) 34, (byte) 5);
		lightFilters.put((byte) 18, (byte) 2);//Leaves?
		lightFilters.put((byte) 8, (byte) 2);
		lightFilters.put((byte) 9, (byte) 2);
		lightFilters.put((byte) 79, (byte) 2);
		lightFilters.put((byte) 44, (byte) 1);
		lightFilters.put((byte) 126, (byte) 1);
		lightFilters.put((byte) 53, (byte) 1);
		lightFilters.put((byte) 67, (byte) 1);
		lightFilters.put((byte) 108, (byte) 1);
		lightFilters.put((byte) 109, (byte) 1);
		lightFilters.put((byte) 114, (byte) 1);
		lightFilters.put((byte) 128, (byte) 1);
		lightFilters.put((byte) 134, (byte) 1);
		lightFilters.put((byte) 135, (byte) 1);
		lightFilters.put((byte) 136, (byte) 1);
		
		lightFilters.put((byte) 6, (byte) 0);
		lightFilters.put((byte) 20, (byte) 0);
		lightFilters.put((byte) 26, (byte) 0);
		lightFilters.put((byte) 27, (byte) 0);
		lightFilters.put((byte) 28, (byte) 0);
		lightFilters.put((byte) 30, (byte) 0);
		lightFilters.put((byte) 31, (byte) 0);
		lightFilters.put((byte) 32, (byte) 0);
		lightFilters.put((byte) 37, (byte) 0);
		lightFilters.put((byte) 38, (byte) 0);
		lightFilters.put((byte) 39, (byte) 0);
		lightFilters.put((byte) 40, (byte) 0);
		lightFilters.put((byte) 50, (byte) 0);
		lightFilters.put((byte) 51, (byte) 0);
		lightFilters.put((byte) 55, (byte) 0);
		lightFilters.put((byte) 59, (byte) 0);
		lightFilters.put((byte) 63, (byte) 0);
		lightFilters.put((byte) 64, (byte) 0);
		lightFilters.put((byte) 65, (byte) 0);
		lightFilters.put((byte) 66, (byte) 0);
		lightFilters.put((byte) 68, (byte) 0);
		lightFilters.put((byte) 69, (byte) 0);
		lightFilters.put((byte) 70, (byte) 0);
		lightFilters.put((byte) 71, (byte) 0);
		lightFilters.put((byte) 72, (byte) 0);
		lightFilters.put((byte) 75, (byte) 0);
		lightFilters.put((byte) 76, (byte) 0);
		lightFilters.put((byte) 77, (byte) 0);
		lightFilters.put((byte) 83, (byte) 0);
		lightFilters.put((byte) 85, (byte) 0);
		lightFilters.put((byte) 96, (byte) 0);
		lightFilters.put((byte) 101, (byte) 0);
		lightFilters.put((byte) 102, (byte) 0);
		lightFilters.put((byte) 104, (byte) 0);
		lightFilters.put((byte) 105, (byte) 0);
		lightFilters.put((byte) 106, (byte) 0);
		lightFilters.put((byte) 107, (byte) 0);
		lightFilters.put((byte) 111, (byte) 0);
		lightFilters.put((byte) 113, (byte) 0);
		lightFilters.put((byte) 115, (byte) 0);
		lightFilters.put((byte) 117, (byte) 0);
		lightFilters.put((byte) 122, (byte) 0);
		lightFilters.put((byte) 127, (byte) 0);
		lightFilters.put((byte) 131, (byte) 0);
		lightFilters.put((byte) 132, (byte) 0);
		lightFilters.put((byte) 138, (byte) 0);
		lightFilters.put((byte) 139, (byte) 0);
		lightFilters.put((byte) 140, (byte) 0);
		lightFilters.put((byte) 141, (byte) 0);
		lightFilters.put((byte) 142, (byte) 0);
		lightFilters.put((byte) 143, (byte) 0);
	}
}
