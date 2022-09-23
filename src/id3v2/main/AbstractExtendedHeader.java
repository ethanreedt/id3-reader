package id3v2.main;

import java.util.HashMap;

public abstract class AbstractExtendedHeader {
	protected int size;
	protected HashMap<String, ExtendedHeaderFlag> flags;
	
	public AbstractExtendedHeader() {}
	
	public abstract void setFlags(int flags);
	
	public class ExtendedHeaderFlag extends Flag {
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
