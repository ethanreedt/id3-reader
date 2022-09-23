package id3v2.v23;

import id3v2.main.AbstractHeader;
import id3v2.main.AbstractID3v2Tag;
import id3v2.util.SyncSafeInt;

public class ID3v23Tag extends AbstractID3v2Tag {
	
	ID3v23ExtendedHeader extendedHeader;

	public ID3v23Tag(byte[] tag) {
		super(tag);
		byte[] headerBytes = new byte[AbstractHeader.HEADER_SIZE];
		this.content.get(headerBytes);
		this.header = new ID3v23Header(headerBytes);
		if (this.header.getFlags().get("extendedHeader").isSet()) {
			// read ahead for header size
			byte[] ehSizeBytes = new byte[SyncSafeInt.SYNCSAFEINT_SIZE];
			this.content.get(ehSizeBytes);
			int ehSize = SyncSafeInt.toInt(ehSizeBytes);
			byte[] ehRemainingBytes = new byte[ehSize]; // ID3v23 extended header size excludes the size field
			this.content.get(ehRemainingBytes);
			// combine
			byte[] ehTotalBytes = new byte[ehSizeBytes.length + ehRemainingBytes.length];
			int idxTotal = 0;
			for (int i = 0; i < ehSizeBytes.length; i++) {
				ehTotalBytes[idxTotal++] = ehSizeBytes[i];
			}
			for (int i = 0; i < ehRemainingBytes.length; i++) {
				ehTotalBytes[idxTotal++] = ehRemainingBytes[i];
			}
			this.extendedHeader = new ID3v23ExtendedHeader(ehTotalBytes);
		} else {
			this.extendedHeader = null;
		}
	}

}
