package frames;

import java.nio.CharBuffer;

import main.AbstractFrame;

public class AbstractURLLinkFrame extends AbstractFrame {
	String url;

	public AbstractURLLinkFrame(String title, int size, byte[] flags, byte[] content, int version) {
		super(title, size, flags, content, version);
		
		CharBuffer contentString = this.content.asCharBuffer();
		char[] url = new char[contentString.length()];
		
		int idx = 0;
		char current = contentString.get();
		// TODO: Confusion: See id3v2.3.0 4.3 URL Link Frames 
		// 			Is string null terminated or terminated by $00?
		while (current != '\0') {
			url[idx++] = current;
		}
		this.url = new String(url);
	}
}
