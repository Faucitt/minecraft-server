package faucitt.model.entity;

import java.io.IOException;

import faucitt.io.MCSocket;


public class ByteMetaData extends EntityMetaData {
	private byte data;
	
	public ByteMetaData(byte data, byte meta) {
		super(MetaDataType.BYTE, meta);
		setData(data);
	}

	public byte getData() {
		return data;
	}

	public void setData(byte data) {
		this.data = data;
	}

	@Override
	public void write(MCSocket socket) throws IOException {
		socket.writeByte((byte) (this.getType().getId() | (this.getMeta()<<5)));
		socket.writeByte(data);
	}
}
