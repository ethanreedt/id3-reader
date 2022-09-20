package id3v1;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.HashMap;

import main.Tag;

public class ID3v11Tag extends AbstractID3v1Tag implements Tag  {
	static String IDENTIFIER = "EXT";
	static int TAG_SIZE = 256;
	
	HashMap<String,String> info;
	
	public ID3v11Tag(byte[] tag) {
		super(tag);
		this.content = ByteBuffer.wrap(tag);
		this.content.position(ID3v1Tag.TAG_SIZE);
		
		byte[] ID3v1TagBytes = new byte[this.content.remaining()];
		this.content.get(ID3v1TagBytes);
		ID3v1Tag v1Tag = new ID3v1Tag(ID3v1TagBytes);
		
		this.info = new HashMap<String, String>();
		this.info.put("Identifier", getStringFromContent(3));
		this.info.put("Song Name", v1Tag.info.get("Identifier") + getStringFromContent(30));
		this.info.put("Artist", v1Tag.info.get("Artist") + getStringFromContent(30));
		this.info.put("Album Name", v1Tag.info.get("Album Name") + getStringFromContent(30));
		this.info.put("Comment", v1Tag.info.get("Comment").substring(0, 29) + getStringFromContent(15));
		this.info.put("SubGenre", getStringFromContent(20));
		this.info.put("Genre", v1Tag.info.get("Genre"));
		this.info.put("Track Position", v1Tag.info.get("Comment").substring(29, 31));
	}
	
	public static ID3v11Tag findTag(File file) throws IOException {
		return new ID3v11Tag(ID3v11Tag.findTagBytes(file));
	}
	
	public static byte[] findTagBytes(File file) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(file, "r");
		long tagStart = file.length() - TAG_SIZE;
		raf.seek(tagStart);
		
		byte[] identifierBytes = new byte[3];
		raf.read(identifierBytes);
		
		if (IDENTIFIER == new String(identifierBytes)) {
			raf.seek(tagStart);
			byte[] tag = new byte[TAG_SIZE];
			raf.read(tag);
			raf.close();
			return tag;
		} else {
			raf.close();
			return null;
		}
	}
}
