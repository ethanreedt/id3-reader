package id3v2.v23;

import java.nio.ByteBuffer;

import id3v2.main.AbstractExtendedHeader;

public class ID3v23ExtendedHeader extends AbstractExtendedHeader {

	public int paddingSize;
	
	public ID3v23ExtendedHeader(byte[] ehBytes) {
		super();
		ByteBuffer bb = ByteBuffer.wrap(ehBytes); // ID3v23 does not use syncsafe int for sizes in the extended header.
		this.size = bb.getInt();
		setFlags(bb.get()); // ID3v23 only reads first bit of first byte. ID3v24 changes flag scheme.
		bb.get(); // blank byte (second byte of flag)
		this.paddingSize = bb.getInt();
		flagDataPopulate(bb); // flag data is appended to extended header
	}

	public void setFlags(int flags) {
		this.flags.put("CRCDataPresent", new ExtendedHeaderFlag("CRCDataPresent", (flags & 32) > 0));
	}

	public void flagDataPopulate(ByteBuffer content) {
		if (this.flags.get("CRCDataPresent").isSet()) {
			this.flags.get("CRCDataPresent").setData(content.getInt());
		}
		
	}

}
