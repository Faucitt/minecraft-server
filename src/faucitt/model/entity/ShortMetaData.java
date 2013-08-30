package faucitt.model.entity;

import java.io.IOException;

import faucitt.io.MCSocket;


public class ShortMetaData extends EntityMetaData {
	private short data;
	
	public ShortMetaData(short data, byte meta) {
		super(MetaDataType.SHORT, meta);
		setData(data);
	}

	public short getData() {
		return data;
	}

	public void setData(short data) {
		this.data = data;
	}
	
	@Override
	public void write(MCSocket socket) throws IOException {
		socket.writeByte((byte) (this.getType().getId() | (this.getMeta()<<5)));
		socket.writeShort(data);
	}
}
