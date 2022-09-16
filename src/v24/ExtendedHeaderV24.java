package v24;

import java.util.ArrayList;
import java.util.HashMap;

import main.Flag;
import main.Frame;
import util.ReadHelper;

public class ExtendedHeaderV24 {
	int size;
	int numOfFlagBytes;
	HashMap<String, ExtendedHeaderFlag> flags;
	HashMap<String, Integer> restrictions;
	ReadHelper rh;
	

//	public ExtendedHeader(byte[] extendedHeaderBytes, ReadHelper rh) {
//		this.rh = rh;
//		
//		byte[] sizeBytes = {extendedHeaderBytes[0], extendedHeaderBytes[1], extendedHeaderBytes[2], extendedHeaderBytes[3]};
//		this.size = SyncSafeInt.toInt(sizeBytes);
//		
//		this.numOfFlagBytes = extendedHeaderBytes[4];
//		
//		int flag = extendedHeaderBytes[5];
//		this.flags.put("tagIsAnUpdate", new ExtendedHeaderFlag("tagIsAnUpdate", (flag & 64) > 0));
//		this.flags.put("CRCDataPresent", new ExtendedHeaderFlag("CRCDataPresent", (flag & 32) > 0));
//		this.flags.put("tagRestrictions", new ExtendedHeaderFlag("tagRestrictions", (flag & 16) > 0));
//		// TODO Auto-generated constructor stub
//	}

	public ExtendedHeaderV24(int size, int numOfFlagBytes, int flags, byte[] content) {
		this.size = size;
		this.numOfFlagBytes = numOfFlagBytes;
		this.flags.put("tagIsAnUpdate", new ExtendedHeaderFlag("tagIsAnUpdate", (flags & 64) > 0));
		this.flags.put("CRCDataPresent", new ExtendedHeaderFlag("CRCDataPresent", (flags & 32) > 0));
		this.flags.put("tagRestrictions", new ExtendedHeaderFlag("tagRestrictions", (flags & 16) > 0));
		// TODO: Use `content` to add context to flags
	}

	public void verifyTagRestrictions(ArrayList<Frame> frames) {
		// TODO Auto-generated method stub
		
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
