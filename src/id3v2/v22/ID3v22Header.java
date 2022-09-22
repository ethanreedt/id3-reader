package id3v2.v22;

import id3v2.main.AbstractHeader;

public class ID3v22Header extends AbstractHeader {
	public ID3v22Header(String identifier, int version, int revision, int flags, int size) throws IllegalArgumentException {
		super(identifier, version, revision, flags, size);
	}
	
	public ID3v22Header(byte[] headerBytes) {
		super(headerBytes);
	}
	
	public void setFlags(int flags) throws IllegalArgumentException {
		this.flags.put("unsynchronisation", new HeaderFlag("unsynchronisation", (flags & 128) > 0));
		this.flags.put("compression", new HeaderFlag("compression", (flags & 64) > 0));
		if ((Integer.parseInt("00111111", 2) & flags) > 0) { 
			throw new IllegalArgumentException();
		}
	}
	
}
