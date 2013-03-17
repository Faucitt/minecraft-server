package server.model.entity;

import server.model.inventory.Slot;

public class SlotMetaData extends EntityMetaData {

	private Slot data;
	
	public SlotMetaData(Slot data) {
		super(MetaDataType.SLOT);
		setData(data);
	}

	public Slot getData() {
		return data;
	}

	public void setData(Slot data) {
		this.data = data;
	}

}
