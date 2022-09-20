package id3v2.frames;

import id3v2.main.AbstractFrame;

public class AbstractTextInformationFrame extends AbstractFrame {
	int encoding;
	String information;
	
	public AbstractTextInformationFrame(String title, int size, byte[] flags, byte[] content, int version) {
		super(title, size, flags, content, version);
		this.encoding = this.content.get();
		this.information = this.readNextString();
	}
}
