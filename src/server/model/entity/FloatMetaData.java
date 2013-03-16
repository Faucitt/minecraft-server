package server.model.entity;

public class FloatMetaData extends EntityMetaData {
	private float data;
	
	public FloatMetaData(float data) {
		super(MetaDataType.FLOAT);
		setData(data);
	}

	public float getData() {
		return data;
	}

	public void setData(float data) {
		this.data = data;
	}
}
