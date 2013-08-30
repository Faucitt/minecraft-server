package faucitt.model.inventory;

import java.util.List;

import faucitt.nbt.Tag;


public class Slot {
	private short id, metaData;
	private byte amount;
	
	private List<Tag> nbtTags;
	
	public short getId() {
		return id;
	}
	public void setId(short id) {
		this.id = id;
	}
	public short getMetaData() {
		return metaData;
	}
	public void setMetaData(short metaData) {
		this.metaData = metaData;
	}
	public byte getAmount() {
		return amount;
	}
	public void setAmount(byte amount) {
		this.amount = amount;
	}
	public List<Tag> getNbtTags() {
		return nbtTags;
	}
	public void setNbtTags(List<Tag> nbtTags) {
		this.nbtTags = nbtTags;
	}
	
}
