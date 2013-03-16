package server.model.entity;

public class ByteMetaData extends EntityMetaData {
	private byte data;
	
	public ByteMetaData(byte data) {
		super(MetaDataType.BYTE);
		setData(data);
	}

	public byte getData() {
		return data;
	}

	public void setData(byte data) {
		this.data = data;
	}
}
