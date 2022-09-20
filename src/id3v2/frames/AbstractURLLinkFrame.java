package id3v2.frames;

import id3v2.main.AbstractFrame;

public class AbstractURLLinkFrame extends AbstractFrame {
	String url;

	public AbstractURLLinkFrame(String title, int size, byte[] flags, byte[] content, int version) {
		super(title, size, flags, content, version);
		this.url = this.readNextString();
	}
}
