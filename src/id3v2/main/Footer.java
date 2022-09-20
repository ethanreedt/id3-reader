package id3v2.main;

import java.util.HashMap;

public class Footer {
	String id;
	int version;
	int revision;
	HashMap<String, FooterFlag> flags;
	int size;

	public Footer(String id, int version, int revision, int flags, int size) {
		this.id = id;
		this.version = version;
		this.revision = revision;
		this.flags.put("unsynchronisation", new FooterFlag("unsynchronisation", (flags & 128) > 0));
		this.flags.put("extendedHeader", new FooterFlag("extendedHeader", (flags & 64) > 0));
		this.flags.put("experimentalIndicator", new FooterFlag("experimentalIndicator", (flags & 32) > 0));
		this.flags.put("footerPresent", new FooterFlag("footerPresent", (flags & 16) > 0));
		this.size = size;
	}
	
	public class FooterFlag extends Flag {
		public FooterFlag(String description, boolean isSet) {
			super(description, isSet);
		}
	}
}