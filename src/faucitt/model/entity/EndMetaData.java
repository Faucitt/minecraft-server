package faucitt.model.entity;

import java.io.IOException;

import faucitt.io.MCSocket;


public class EndMetaData extends EntityMetaData {

	public EndMetaData() {
		super(MetaDataType.END, (byte) 0x1F);
	}

	@Override
	public void write(MCSocket socket) throws IOException {
		socket.writeByte((byte) 127);
	}
	
}
