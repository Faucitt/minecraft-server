package server.util;

public enum InventoryType {
	CHEST(0),
	WORKBENCH(1),
	FURNACE(2),
	DISPENSER(3),
	ENCHANTMENT_TABLE(4),
	BREWING_STAND(5);
	
	private static final InventoryType[] types = new InventoryType[6];
	
	static {
		for (InventoryType type : InventoryType.values()) {
			types[type.id] = type;
		}
	}
	
	private int id;
	
	InventoryType(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public static InventoryType getForId(int id) {
		return types[id];
	}
}
