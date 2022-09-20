package util;

import java.nio.ByteBuffer;

public class TagContent {
	ByteBuffer content;
	
	public TagContent(byte[] content) {
		this.content = ByteBuffer.wrap(content);
	}
	
	public ByteBuffer getBuffer() {
		return this.content;
	}
}
