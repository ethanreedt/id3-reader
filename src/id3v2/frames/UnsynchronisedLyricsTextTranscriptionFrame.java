package id3v2.frames;

import id3v2.main.AbstractFrame;

public class UnsynchronisedLyricsTextTranscriptionFrame extends AbstractFrame {
	int encoding;  // TODO: incorporate encoding into string reading
	byte[] language;
	String contentDescriptior;
	String lyricsText;
	
	public UnsynchronisedLyricsTextTranscriptionFrame(String title, int size, byte[] flags, byte[] content,
			int version) {
		super(title, size, flags, content, version);
		this.encoding = this.content.get();
		this.language = new byte[3];
		this.content.get(this.language);
		this.contentDescriptior = this.readNextString();
		byte[] lyricsTextBytes = new byte[this.content.remaining()];
		this.content.get(lyricsTextBytes);
		this.lyricsText = new String(lyricsTextBytes);
	}

}
