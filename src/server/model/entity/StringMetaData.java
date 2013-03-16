package server.model.entity;

public class StringMetaData extends EntityMetaData {
	private String data;
	
	public StringMetaData(String data) {
		super(MetaDataType.STRING);
		setData(data);
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
