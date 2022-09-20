import java.io.File;

import id3v1.ID3v11Tag;
import id3v1.ID3v1Tag;
import id3v2.v22.ID3v22Tag;

public class ID3Reader {
	enum SUPPORTED_VERSIONS {V1, V11, V22, V23, V24};
	Tag tag;
	
	public void findTag(File file) {
		this.tag = null;
		
		SUPPORTED_VERSIONS[] values = SUPPORTED_VERSIONS.values();
		for (int i = values.length; i >= 0; i--) {
			SUPPORTED_VERSIONS version = values[i];
			switch (version) {
			case V24:
				this.tag = ID3v24Tag.findTag(file);
				break;
			case V23:
				this.tag = ID3v23Tag.findTag(file);
				break;
			case V22:
				this.tag = ID3v22Tag.findTag(file);
				break;
			case V11:
				this.tag = ID3v11Tag.findTag(file);
				break;
			case V1:
				this.tag = ID3v1Tag.findTag(file);
				break;
			}
			if (tag != null) {
				return;
			}
		}
		// Search tags from most recent to oldest
		
		// ID3V2 search
		//		Look for prepended tag
		//		If SEEK frame in tag, use its values to guide further searching
		//		Look for a tag footer from the back of the file
		
		
		// ID3V1 search
		// 		Go back 128 bytes from end of file and look for TAG string
		
		// 		ID3V1.1 search
		//			Go back 256 bytes from end of file and look for EXT string
		
		// No tag found
	}
}
