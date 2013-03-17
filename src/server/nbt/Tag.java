package server.nbt;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import server.util.CompressionType;

public abstract class Tag {
	
	public enum TagType {
		TAG_END(0),
		TAG_BYTE(1),
		TAG_SHORT(2),
		TAG_INT(3),
		TAG_LONG(4),
		TAG_FLOAT(5),
		TAG_DOUBLE(6),
		TAG_BYTE_ARRAY(7),
		TAG_STRING(8),
		TAG_LIST(9),
		TAG_COMPOUND(10),
		TAG_INT_ARRAY(11);
		
		private final int id;
		
		private static final TagType[] types = new TagType[12];
		
		static {
			for (TagType type : TagType.values()) {
				types[type.id] = type;
			}
		}
		
		TagType(int id) {
			this.id = id;
		}
		
		public int getId() {
			return id;
		}
		
		public static TagType getForId(int id) {
			return types[id];
		}
	}
	
	private TagType type;
	
	public Tag(TagType type) {
		this.type = type;
	}
	
	public TagType getType() {
		return type;
	}
	
	public static List<Tag> parse(InputStream in, CompressionType type) {
		switch(type) {
		case GZIP:
			return parseGzip((GZIPInputStream) in);
		default:
			return null;
		}
	}
	
	public static List<Tag> parseGzip(GZIPInputStream in) {
		List<Tag> tags = new ArrayList<>();
		//TODO
		return tags;
	}
	
	public abstract Tag read(InputStream in) throws IOException;
}
