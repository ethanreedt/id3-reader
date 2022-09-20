package id3v2.main;

import java.util.HashMap;

import id3v2.util.SyncSafeInt;

public class Header {
	String id;
	int version;
	int revision;
	HashMap<String, HeaderFlag> flags;
	int size;

	public Header(String id, int version, int revision, int flags, int size) throws IllegalArgumentException {
		this.id = id;
		setVersion(version);
		setRevision(revision);
		setFlags(flags);
		
		if ((flags & Integer.parseInt("00001111", 2)) > 0) {
			throw new IllegalArgumentException(); 
		}
		this.flags = new HashMap<String, HeaderFlag>();
		this.flags.put("unsynchronisation", new HeaderFlag("unsynchronisation", (flags & 128) > 0));
		this.flags.put("extendedHeader", new HeaderFlag("extendedHeader", (flags & 64) > 0));
		this.flags.put("experimentalIndicator", new HeaderFlag("experimentalIndicator", (flags & 32) > 0));
		this.flags.put("footerPresent", new HeaderFlag("footerPresent", (flags & 16) > 0));
		
		this.size = size;
	}
	
	// TODO: Consider moving location of this method. Possibly to an abstracted `TagContent` class that manages the ByteBuffer for the whole tag.
	public static Header headerFromBytes (byte[] headerBytes) {
		byte[] idBytes = {headerBytes[0], headerBytes[1], headerBytes[2]};
		String id = new String(idBytes);
		
		int version = headerBytes[3];
		int revision = headerBytes[4];
		
		int flags = headerBytes[5];
		
		byte[] sizeBytes = {headerBytes[6], headerBytes[7], headerBytes[8], headerBytes[9]};
		int size = SyncSafeInt.toInt(sizeBytes);
		return new Header(id, version, revision, flags, size);
	}
	
	public HashMap<String, HeaderFlag> getFlags() {
		return this.flags;
	}
	
	private void setVersion(int version) throws IllegalArgumentException {
		if (version == 255) throw new IllegalArgumentException();
		this.version = version;
	}
	
	private void setRevision(int revision) throws IllegalArgumentException {
		if (revision == 255) throw new IllegalArgumentException();
		this.revision = revision;
	}
	
	private void setFlags(int flags) throws IllegalArgumentException {
		
	}
	
	public class HeaderFlag extends Flag {
		public HeaderFlag(String description, boolean isSet) {
			super(description, isSet);
		}
	}
}
