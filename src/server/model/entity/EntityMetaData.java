package server.model.entity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import server.io.MCSocket;
import server.model.inventory.Slot;
import server.nbt.Tag;
import server.packet.StatusPacket.ClientStatus;
import server.util.CompressionType;

public abstract class EntityMetaData {
	
	public enum MetaDataType {
		BYTE(0),
		SHORT(1),
		INT(2),
		FLOAT(3),
		STRING(4),
		SLOT(5),
		POSITION(6);
		
		private static final MetaDataType[] types = new MetaDataType[2];
		
		static {
			for (MetaDataType type : MetaDataType.values()) {
				types[type.id] = type;
			}
		}
		
		private int id;
		
		MetaDataType(int id) {
			this.id = id;
		}
		
		public int getId() {
			return id;
		}
		
		public static MetaDataType getForId(int id) {
			return types[id];
		}
	}
	
	MetaDataType type;
	public EntityMetaData(MetaDataType type) {
		this.type = type;
	}
	
	public MetaDataType getType() {
		return type;
	}
	
	public static List<EntityMetaData> readEntityMetaData(MCSocket socket) throws IOException {
		List<EntityMetaData> list = new ArrayList<>();
		
		for (byte type = socket.readByte(); type != 127; type = socket.readByte()) {
			switch(MetaDataType.getForId(type)) {
			case BYTE:
				list.add((EntityMetaData) new ByteMetaData(socket.readByte()));
				break;
				
			case SHORT:
				list.add((EntityMetaData) new ShortMetaData(socket.readShort()));
				break;
				
			case INT:
				list.add((EntityMetaData) new IntMetaData(socket.readInt()));
				break;
				
			case FLOAT:
				list.add((EntityMetaData) new FloatMetaData(socket.readFloat()));
				break;
				
			case STRING:
				list.add((EntityMetaData) new StringMetaData(socket.readString()));
				break;
				
			case SLOT:
				short itemId = socket.readShort();
				if (itemId != -1) {
					byte amount = socket.readByte();
					short metaData = socket.readShort();
					short nbtLength = socket.readShort();
					Slot slot = new Slot();
					if (nbtLength != -1) {
						byte[] nbtData = socket.readByteArray();
						GZIPInputStream input = new GZIPInputStream(new ByteArrayInputStream(nbtData));
						List<Tag> nbtTags = Tag.parse(input, CompressionType.GZIP);
						slot.setNbtTags(nbtTags);
					}
					slot.setId(itemId);
					slot.setAmount(amount);
					slot.setMetaData(metaData);
				}
				break;
				
			case POSITION:
				list.add((EntityMetaData) new PositionMetaData(socket.readInt(), socket.readInt(), socket.readInt()));
				break;
			}
		}
		
		return list;
	}
}
