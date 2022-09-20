package id3v1;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public abstract class AbstractID3v1Tag {
	ByteBuffer content;
	
	public AbstractID3v1Tag(byte[] tag) {
		this.content = ByteBuffer.wrap(tag);
	}
	
	protected String getStringFromContent(int length) {
		byte[] bytes = new byte[length];
		this.content.get(bytes);
		return new String(bytes);
	}
	
	protected static byte[] findTagBytes(File file, String identifier, int tagSize) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(file, "r");
		long tagStart = file.length() - tagSize;
		raf.seek(tagStart);
		
		byte[] identifierBytes = new byte[3];
		raf.read(identifierBytes);
		
		if (identifier == new String(identifierBytes)) {
			raf.seek(tagStart);
			byte[] tag = new byte[tagSize];
			raf.read(tag);
			raf.close();
			return tag;
		} else {
			raf.close();
			return null;
		}
	}
	
	
}
