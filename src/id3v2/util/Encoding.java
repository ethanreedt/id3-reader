package id3v2.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Encoding {
	String encoding;
	byte[] termination;
	public Charset charset;
	public boolean isUTF16; // TODO: used in readNextString. May be a better way to do this.
	     
	
	public Encoding(int value) {
		switch (valueToEncodingType(value)) {
		case "ISO-8859-1":
			this.encoding = "ISO-8859-1";
			this.termination = new byte[] {00};
			this.charset = StandardCharsets.ISO_8859_1;
		case "UTF-16": // TODO: termination is a decision made by byte contents
			this.encoding = "UTF-16";
			this.termination = new byte[] {00, 00};
			this.charset = StandardCharsets.UTF_16;
		case "UTF-16BE":
			this.encoding = "UTF-16";
			this.termination = new byte[] {00, 00};
			this.charset = StandardCharsets.UTF_16BE;
		case "UTF-8":
			this.encoding = "UTF-8";
			this.termination = new byte[] {00};
			this.charset = StandardCharsets.UTF_8;
		}
	}
	
	// TODO: Need this in an Enum
	public String valueToEncodingType(int value) {
		if (value == 0) return "ISO-8859-1";
		if (value == 1) return "UTF-16";
		if (value == 2) return "UTF-16BE";
		return "UTF-8"; // TODO: Better handling of this
	}
}
