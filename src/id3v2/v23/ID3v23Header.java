package id3v2.v23;

import id3v2.main.AbstractHeader;

public class ID3v23Header extends AbstractHeader{

	public ID3v23Header(String identifier, int version, int revision, int flags, int size) throws IllegalArgumentException {
		super(identifier, version, revision, flags, size);
	}
	
	public ID3v23Header(byte[] headerBytes) throws IllegalArgumentException {
		super(headerBytes);
	}

	public void setFlags(int flags) throws IllegalArgumentException {
		this.flags.put("unsynchronisation", new HeaderFlag("unsynchronisation", (flags & 128) > 0));
		this.flags.put("extendedHeader", new HeaderFlag("extendedHeader", (flags & 64) > 0)); // TODO: Maybe change flag names from camel case?
		this.flags.put("experimentalIndicator", new HeaderFlag("experimentalIndicator", (flags & 32) > 0));
		if ((Integer.parseInt("00011111", 2) & flags) > 0) { 
			throw new IllegalArgumentException();
		}
	}

}
