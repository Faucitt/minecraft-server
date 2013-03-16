package server.model.inventory;

public class Slot {
	private short id, metadata;
	private byte amount;
	
	
	
	public short getId() {
		return id;
	}
	public void setId(short id) {
		this.id = id;
	}
	public short getMetadata() {
		return metadata;
	}
	public void setMetadata(short metadata) {
		this.metadata = metadata;
	}
	public byte getAmount() {
		return amount;
	}
	public void setAmount(byte amount) {
		this.amount = amount;
	}
	
}
