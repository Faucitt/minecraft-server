package server.nbt;

import java.io.IOException;
import java.io.InputStream;

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

	@Override
	public Tag read(InputStream in) throws IOException {
		TagByte tag = new TagByte((byte) in.read());
		return tag;
	}
	
}
