package server.model.entity;

import java.io.IOException;

import server.io.MCSocket;

public class StringMetaData extends EntityMetaData {
	private String data;
	
	public StringMetaData(String data, byte meta) {
		super(MetaDataType.STRING, meta);
		setData(data);
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	@Override
	public void write(MCSocket socket) throws IOException {
		socket.writeByte((byte) (this.getType().getId() | (this.getMeta()<<5)));
		socket.writeString(data);
	}
}
