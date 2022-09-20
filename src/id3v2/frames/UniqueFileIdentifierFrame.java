package id3v2.frames;

import id3v2.main.AbstractFrame;

public class UniqueFileIdentifierFrame extends AbstractFrame {
	String ownerIdentifier;
	byte[] identifier;

	public UniqueFileIdentifierFrame(String title, int size, byte[] flags, byte[] content, int version) {
		super(title, size, flags, content, version);
		this.ownerIdentifier = this.readNextString();
		byte[] binaryIdentifier = new byte[this.content.remaining()];
		if (this.content.remaining() > 64) {
			// TODO: throw some sort of error here (?)
		}
		this.content.get(binaryIdentifier); 
		this.identifier = binaryIdentifier;
	}

}
