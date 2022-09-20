package id3v1;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import main.Tag;

public class ID3v1Tag extends AbstractID3v1Tag implements Tag {
	static String IDENTIFIER = "TAG";
	static int TAG_SIZE = 128;
	
	HashMap<String,String> info;
	
	public ID3v1Tag(byte[] tag) {
		super(tag);
		this.info = new HashMap<String, String>();
		this.info.put("Identifier", getStringFromContent(3));
		this.info.put("Song Name", getStringFromContent(30));
		this.info.put("Artist", getStringFromContent(30));
		this.info.put("Album Name", getStringFromContent(30));
		this.info.put("Year", getStringFromContent(4));
		this.info.put("Comment", getStringFromContent(30));
		this.info.put("Genre", Genre.getID3v1Genre(this.content.get()));
	}
	
	public static ID3v1Tag findTag(File file) throws IOException {
		return new ID3v1Tag(AbstractID3v1Tag.findTagBytes(file, IDENTIFIER, TAG_SIZE));
	}
}
