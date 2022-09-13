package main;

import java.util.HashMap;

public class Footer {
	String id;
	int version;
	int revision;
	HashMap<String, Boolean> flags;
	int size;

	public Footer(String id, int version, int revision, int flags, int size) {
		this.id = id;
		this.version = version;
		this.revision = revision;
		this.flags.put("unsynchronisation", (flags & 128) > 0);
		this.flags.put("extendedHeader", (flags & 64) > 0);
		this.flags.put("experimentalIndicator", (flags & 32) > 0);
		this.flags.put("footerPresent", (flags & 16) > 0);
		this.size = size;
	}
}