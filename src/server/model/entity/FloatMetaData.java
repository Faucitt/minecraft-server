package server.model.entity;

import java.io.IOException;

import server.io.MCSocket;

public class FloatMetaData extends EntityMetaData {
	private float data;
	
	public FloatMetaData(float data, byte meta) {
		super(MetaDataType.FLOAT, meta);
		setData(data);
	}

	public float getData() {
		return data;
	}

	public void setData(float data) {
		this.data = data;
	}

	@Override
	public void write(MCSocket socket) throws IOException {
		socket.writeByte((byte) (this.getType().getId() | (this.getMeta()<<5)));
		socket.writeFloat(data);
	}
}
