package id3v2.main;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public abstract class AbstractID3v2Tag {
	public static String IDENTIFIER = "ID3";

	protected AbstractHeader header;
	protected ByteBuffer content;
	
	public AbstractID3v2Tag(byte[] tag) {
		this.content = ByteBuffer.wrap(tag);
	}
	
	public static byte[] findTagBytes(File file) throws IOException {
		// Header: $49 44 33 yy yy xx zz zz zz zz
		// Footer: $33 44 49 yy yy xx zz zz zz zz
		
		// Look for header 
		RandomAccessFile raf = new RandomAccessFile(file, "r");
		// TODO: add error checking for end of file?
		byte[] identifierBytes = new byte[3];
		raf.read(identifierBytes);
		if (IDENTIFIER.equals(new String(identifierBytes))) {
			byte[] versionBytes = new byte[2];
			raf.read(versionBytes); // $yy yy
			if (versionBytes[0] == versionBytes[1]) {
				raf.read(); // $xx
				byte[] sizeBytes = new byte[4];
				raf.read(sizeBytes); // $zz zz zz zz
				boolean tagAtBeginning = true; // TODO: would prefer to avoid flags
				for (int i = 1; i < 4; i++) {
					if (sizeBytes[0] != sizeBytes[i]) tagAtBeginning = false;
				}
				if (tagAtBeginning) {
					raf.seek(0);
					byte[] headerBytes = new byte[AbstractHeader.HEADER_SIZE];
					raf.read(headerBytes);
					ID3v24Header header = new ID3v24Header(headerBytes); // versions within release are backwards compatible. Using ID3v24 for footer and size is reliable.
					int size = header.size + AbstractHeader.HEADER_SIZE; // size of tag + size of header
					size += (header.flags.get("footerPresent") != null && header.flags.get("footerPresent").isSet()) ? 10 : 0; // TODO: Potentially refactor to `Header` method (`hasSetFlag(String)`)
					raf.seek(0);
					byte[] tagBytes = new byte[size];
					raf.read(tagBytes);
					raf.close();
					return tagBytes;
				}
			}
		}

		// Look for footer
		long footerLocation = (file.length() - 1) - 10; // TODO: double check (in testing) that it should be `file.length() - 1` and not just `file.length()`
		raf.seek(footerLocation);
		String footerIdentifier = "3DI";
		byte[] footerIdentifierBytes = new byte[3];
		raf.read(footerIdentifierBytes);
		if (footerIdentifier.equals(new String(footerIdentifierBytes))) {
			byte[] versionBytes = new byte[2];
			raf.read(versionBytes); // $yy yy
			if (versionBytes[0] == versionBytes[1]) {
				raf.read(); // $xx
				byte[] sizeBytes = new byte[4];
				raf.read(sizeBytes); // $zz zz zz zz
				boolean tagAtEnd = true; // TODO: would prefer to avoid flags
				for (int i = 1; i < 4; i++) {
					if (sizeBytes[0] != sizeBytes[i]) tagAtEnd = false;
				}
				if (tagAtEnd) {
					raf.seek(0);
					byte[] footerBytes = new byte[10];
					Header footer = Header.headerFromBytes(footerBytes);
					int size = footer.size + 10; // size of tag + size of header
					size += 10; // we know footer is set
					long tagAtEndLocation = (file.length() - 1) - size;// TODO: double check (in testing) that it should be `file.length() - 1` and not just `file.length()`
					raf.seek(tagAtEndLocation);
					byte[] tagBytes = new byte[size];
					raf.read(tagBytes);
					raf.close();
					return tagBytes;
				}
			}
		}
		raf.close();
		return null;
	}
	
	public AbstractHeader getHeader() {
		return this.header;
	}
}
