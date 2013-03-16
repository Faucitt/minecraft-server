package server.nbt;

import java.util.ArrayList;
import java.util.List;

import server.io.MCSocket;

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
	
	public static List<Tag> parse(MCSocket socket) {
		List<Tag> tags = new ArrayList<>();
		return tags;
	}
}
