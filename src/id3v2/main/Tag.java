package id3v2.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import v24.ExtendedHeaderV24;


public class Tag {
	Header header;
	ExtendedHeaderV24 extendedHeader;
	TagByteContent content;
	ArrayList<Frame> frames;
	Padding padding;
	Footer footer;
	
	public Tag(File file) {
		this.content = new TagByteContent(file);
		this.header = content.getHeader();
		if (header.flags.get("extendedHeader").isSet()) {
			this.extendedHeader = content.getExtendedHeaderV24();
		}
		
		while (content.hasNextFrame()) {
			frames.add(content.getNextFrame());
		}
		
		if (header.flags.get("footerPresent").isSet()) {
			this.footer = content.getFooter();
			validateFooter(header, footer);
		}
		
		extendedHeader.verifyTagRestrictions(frames);
	}
	
	// TODO: There may be a better place to put this
	// TODO: Refactor
	public static boolean validateFooter(Header h, Footer f) {
		assert(h.id == "ID3");
		assert(f.id == "3DI");
		assert(h.version == f.version);
		assert(h.revision == f.revision);
		// TODO: This is not the right way to perform this check
		for (Map.Entry<String, Header.HeaderFlag> set : h.flags.entrySet()) { 
			assert(set.getValue().isSet() == f.flags.get(set.getKey()).isSet());
		}
		assert(h.size == f.size);
		return true;
	}
}
