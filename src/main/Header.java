package main;

import java.util.HashMap;


public class Header {
	String id;
	int version;
	int revision;
	HashMap<String, Boolean> flags;
	int size;
	
//	public Header(byte[] headerBytes) {
//		byte[] idBytes = {headerBytes[0], headerBytes[1], headerBytes[2]};
//		this.id = new String(idBytes);
//		
//		this.version = headerBytes[3];
//		this.revision = headerBytes[4];
//		
//		assert(this.version < 255);
//		assert(this.revision < 255);
//		
//		int flagByte = headerBytes[5];
//		flags.put("unsynchronisation", (flagByte & 128) > 0);
//		flags.put("extendedHeader", (flagByte & 64) > 0);
//		flags.put("experimentalIndicator", (flagByte & 32) > 0);
//		flags.put("footerPresent", (flagByte & 16) > 0);
//		
//		byte[] sizeBytes = {headerBytes[6], headerBytes[7], headerBytes[8], headerBytes[9]};
//		assert(SyncSafeInt.isSyncSafeInt(sizeBytes));
//		this.size = SyncSafeInt.toInt(sizeBytes);
//	}

	public Header(String id, int version, int revision, int flags, int size) {
		this.id = id;
		this.version = version;
		this.revision = revision;
		this.flags.put("unsynchronisation", (flags & 128) > 0);
		this.flags.put("extendedHeader", (flags & 64) > 0);
		this.flags.put("experimentalIndicator", (flags & 32) > 0);
		this.flags.put("footerPresent", (flags & 16) > 0);
		this.size = size;
		// TODO Auto-generated constructor stub
	}
}
