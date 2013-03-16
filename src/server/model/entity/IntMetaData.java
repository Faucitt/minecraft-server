package server.model.entity;

public class IntMetaData extends EntityMetaData {
	private int data;
	
	public IntMetaData(int data) {
		super(MetaDataType.INT);
		setData(data);
	}

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}
}
