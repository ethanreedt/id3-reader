package main;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import util.ReadHelper;

public class Tag {
	Header header;
	ExtendedHeader extendedHeader;
	TagByteContent content;
	ArrayList<Frame> frames;
	int padding;
	Header footer;
	
	public Tag() {
		this.content = new TagByteContent();
		this.header = content.getHeader();
		if (header.flags.get("extendedHeader")) {
			this.extendedHeader = tbc.getExtendedHeader();
		}
		
		// Only needs to be done per flag
		//
		//if (header.flags.get("unsynchronisation")) {
		//	unsynchroniseFrames(this.frames);
		//}
		
		while (content.hasNextFrame()) {
			frames.add(content.getNextFrame());
		}
		
		if (header.flags.get("footerPresent")) {
			this.footer = content.getFooter();
		}
		
		extendedHeader.verifyTagRestrictions(frames);
	}
	
	public void unsynchroniseFrames(ArrayList<Frame> frames) {
		// replace all $FF 00 with $FF 00 00
		return;
	}
	
	public boolean hasNextFrame() {
		return false;
	}
}
