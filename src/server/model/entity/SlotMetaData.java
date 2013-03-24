package server.model.entity;

import java.io.IOException;

import server.io.MCSocket;
import server.model.inventory.Slot;

public class SlotMetaData extends EntityMetaData {

	private Slot data;
	
	public SlotMetaData(Slot data, byte meta) {
		super(MetaDataType.SLOT, meta);
		setData(data);
	}

	public Slot getData() {
		return data;
	}

	public void setData(Slot data) {
		this.data = data;
	}
	
	@Override
	public void write(MCSocket socket) throws IOException {
		socket.writeByte((byte) (this.getType().getId() | (this.getMeta()<<5)));
		//TODO
	}

}
