package id3v2.frames;

import java.util.HashMap;

import id3v2.main.AbstractFrame;
import util.Encoding;

public class SynchronisedLyricTextFrame extends AbstractFrame {
	Encoding encoding;
	byte[] language;
	int timeStampFormat;
	int contentType;
	String contentDescriptor;
	HashMap<String, byte[]> sync; // TODO: rename
	
	public SynchronisedLyricTextFrame(String title, int size, byte[] flags, byte[] content, int version) {
		super(title, size, flags, content, version);
		this.encoding = new Encoding(this.content.get());
		this.language = new byte[3];
		this.content.get(this.language);
		this.timeStampFormat = this.content.get();
		this.contentType = this.content.get();
		this.contentDescriptor = this.readNextString(this.encoding);
		
		while (this.content.hasRemaining()) {
			String syncString = "";
		}
	}
	
	
}
