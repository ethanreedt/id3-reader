package unit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import id3v2.main.Header;

class HeaderTest {
	String id = "ID3";
	int version = 4;
	int revision = 0;
	int flags = Integer.parseInt("11010000", 2); // Unsync, Extended, Footer
	int size = 20971520; // 20 MiB
	
	@Test
	void testFlags() {
		Header header = new Header(id, version, revision, flags, size);
		assertEquals(header.getFlags().size(), 4); // Four flags total
		// Only three flags set
		assertTrue(header.getFlags().get("unsynchronisation").isSet());
		assertTrue(header.getFlags().get("extendedHeader").isSet());
		assertFalse(header.getFlags().get("experimentalIndicator").isSet());
		assertTrue(header.getFlags().get("footerPresent").isSet());
	}
	
	@Test
	void testInvalidVersionAndRevision() {
		int invalidVersion = 255; // $FF
		int invalidRevision = 255; // $FF
		
		assertThrows(IllegalArgumentException.class, () -> {
			new Header(
				"ID3", 
				invalidVersion,
				revision,
				flags,
				size
				);
		});
		
		assertThrows(IllegalArgumentException.class, () -> {
			new Header(
					"ID3", 
					version,
					invalidRevision,
					flags,
					size
					);
		});
	}
	
	@Test
	void testInvalidV23Flags() {
		byte invalidFlags = Byte.parseByte("1101000", 2); // Invalid flag set (bit 4)
		
		assertThrows(IllegalArgumentException.class, () -> {
			new Header(
					"ID3", 
					3,
					0,
					invalidFlags,
					size
					);
		});
	}
	
	@Test
	void testInvalidV24Flags() {
		byte invalidFlags = Byte.parseByte("1101001", 2); // Invalid flag set (bit 0)
		
		assertThrows(IllegalArgumentException.class, () -> {
			new Header(
					"ID3", 
					4,
					0,
					invalidFlags,
					size
					);
		});
	}
}
