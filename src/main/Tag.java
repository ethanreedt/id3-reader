package main;

import java.io.File;
import java.util.ArrayList;


public class Tag {
	Header header;
	ExtendedHeader extendedHeader;
	TagByteContent content;
	ArrayList<Frame> frames;
	int padding;
	Footer footer;
	
	public Tag(File file) {
		this.content = new TagByteContent(file);
		this.header = content.getHeader();
		if (header.flags.get("extendedHeader")) {
			this.extendedHeader = content.getExtendedHeader();
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
}
