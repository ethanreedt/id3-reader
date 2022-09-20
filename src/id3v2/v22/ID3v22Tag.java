package id3v2.v22;

import java.io.File;
import java.io.IOException;

import id3v2.main.AbstractID3v2Tag;

public class ID3v22Tag extends AbstractID3v2Tag {
	
	public ID3v22Tag(byte[] tag) {
		super(tag);
	}
	
	public static ID3v22Tag findTag(File file) throws IOException {
		return new ID3v22Tag(AbstractID3v2Tag.findTagBytes(file));
	}
}
