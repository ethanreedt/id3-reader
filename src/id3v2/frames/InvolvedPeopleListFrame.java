package id3v2.frames;

import java.util.HashMap;

import id3v2.main.AbstractFrame;

public class InvolvedPeopleListFrame extends AbstractFrame {
	int encoding;
	HashMap<String, String> involvedPeople;
	
	public InvolvedPeopleListFrame(String title, int size, byte[] flags, byte[] content, int version) {
		super(title, size, flags, content, version);
		this.encoding = this.content.get();
		
		// TODO: IMPORTANT: May need better error checking for end of frame
		while (this.content.hasRemaining()) {
			String involvement = this.readNextString();
			String involvee = this.readNextString();
			this.involvedPeople.put(involvee, involvement);
		}
	}

	
}
