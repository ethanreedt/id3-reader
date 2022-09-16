package v23;

import java.util.HashMap;

import main.Flag;

public class ExtendedHeaderV23 {
	int size;
	int paddingSize;
	HashMap<String, ExtendedHeaderFlag> flags;
	
	public ExtendedHeaderV23(int size, int flags, int paddingSize, byte[] content) {
		this.size = size;
		this.paddingSize = paddingSize;
		this.flags.put("CRCDataPresent", new ExtendedHeaderFlag("CRCDataPresent", (flags & 32) > 0));
		// TODO: Use `content` to add context to flags
	}
	
	class ExtendedHeaderFlag extends Flag {
		int dataLength;
		Integer data;
		
		public ExtendedHeaderFlag(String description, boolean isSet) {
			super(description, isSet);
		}
		
		public void setDataLength(int dataLength) {
			this.dataLength = dataLength;
		}
		
		public void setData(Integer data) {
			this.data = data;
		}
		
		public boolean hasData() {
			return (this.data != null);
		}
	}

}
