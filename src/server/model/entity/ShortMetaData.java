package server.model.entity;

public class ShortMetaData extends EntityMetaData {
	private short data;
	
	public ShortMetaData(short data) {
		super(MetaDataType.SHORT);
		setData(data);
	}

	public short getData() {
		return data;
	}

	public void setData(short data) {
		this.data = data;
	}
}
