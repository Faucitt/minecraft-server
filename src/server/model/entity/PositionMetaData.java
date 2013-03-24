package server.model.entity;

import java.io.IOException;

import server.io.MCSocket;

public class PositionMetaData extends EntityMetaData {
	private int x, y, z;
	
	public PositionMetaData(int x, int y, int z, byte meta) {
		super(MetaDataType.POSITION, meta);
		setX(x);
		setY(y);
		setZ(z);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
	
	@Override
	public void write(MCSocket socket) throws IOException {
		socket.writeByte((byte) (this.getType().getId() | (this.getMeta()<<5)));
		socket.writeInt(x);
		socket.writeInt(y);
		socket.writeInt(z);
	}
	
}
