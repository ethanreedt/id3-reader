package id3v2.frames;

import id3v2.main.AbstractFrame;

public class MusicCDIdentifierFrame extends AbstractFrame {
	byte[] cdToc;

	public MusicCDIdentifierFrame(String title, int size, byte[] flags, byte[] content, int version) {
		super(title, size, flags, content, version);
		byte[] cdTocBinary = new byte[this.content.remaining()];
		this.content.get(cdTocBinary);
		this.cdToc = cdTocBinary;
	}

}
