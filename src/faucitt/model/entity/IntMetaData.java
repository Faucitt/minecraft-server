package faucitt.model.entity;

import java.io.IOException;

import faucitt.io.MCSocket;


public class IntMetaData extends EntityMetaData {
	private int data;
	
	public IntMetaData(int data, byte meta) {
		super(MetaDataType.INT, meta);
		setData(data);
	}

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}
	
	@Override
	public void write(MCSocket socket) throws IOException {
		socket.writeByte((byte) (this.getType().getId() | (this.getMeta()<<5)));
		socket.writeInt(data);
	}
}
