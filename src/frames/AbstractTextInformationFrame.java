package frames;

import main.AbstractFrame;

public class AbstractTextInformationFrame extends AbstractFrame {
	int encoding;
	
	public AbstractTextInformationFrame(String title, int size, byte[] flags, byte[] content, int version) {
		super(title, size, flags, content, version);
		this.encoding = this.content.get();
	}
}
