package server.nbt;

public class TagByte extends Tag {

	private int data;
	
	public TagByte(byte data) {
		super(TagType.TAG_BYTE);
		setData(data);
	}

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}
	
}
