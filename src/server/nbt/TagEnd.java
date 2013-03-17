package server.nbt;

import java.io.IOException;
import java.io.InputStream;

public class TagEnd extends Tag {

	public TagEnd() {
		super(TagType.TAG_END);
	}

	@Override
	public Tag read(InputStream in) throws IOException {
		return new TagEnd();
	}
	
}
