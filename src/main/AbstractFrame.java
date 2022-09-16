package main;

import java.nio.ByteBuffer;
import java.util.HashMap;

import util.SyncSafeInt;

public abstract class AbstractFrame implements Frame {
	String title;
	int size;
	HashMap<String, FrameHeaderFlag> headerFlags;
	protected ByteBuffer content;
	
	public AbstractFrame(String title, int size, byte[] flags, byte[] content, int version) {
		this.title = title;
		this.size = size;
		this.content = ByteBuffer.wrap(content);
	
		this.headerFlags.put("tagAlterPreservation", new FrameHeaderFlag("tagAlterPreservation", (flags[0] & 128) > 0));
		this.headerFlags.put("fileAlterPreservation", new FrameHeaderFlag("frameAlterPreservation", (flags[0] & 64) > 0));
		this.headerFlags.put("readOnly", new FrameHeaderFlag("readOnly", (flags[0] & 32) > 0));
		this.headerFlags.put("compression", new FrameHeaderFlag("compression", (flags[1] & 128) > 0));
		this.headerFlags.put("encryption", new FrameHeaderFlag("encryption", (flags[1] & 64) > 0));
		this.headerFlags.put("groupingIdentity", new FrameHeaderFlag("groupingIdentity", (flags[1] & 32) > 0));
		
		if (this.headerFlags.get("encryption").isSet()) {
			this.headerFlags.get("encryption").setData(this.content.get());
		}
		
		if (this.headerFlags.get("groupingIdentity").isSet()) {
			this.headerFlags.get("groupingIdentity").setData(this.content.get());
		}
		
		if (version == 4) {
			this.headerFlags.put("unsynchronisation", new FrameHeaderFlag("unsynchronisation", (flags[1] & 2) > 0));
			this.headerFlags.put("dataLengthIndicator", new FrameHeaderFlag("dataLengthIndicator", (flags[1] & 1) > 0));	
		
			if (this.headerFlags.get("dataLengthIdentifier").isSet()) {
				byte[] dataLengthIndicatorBytes = new byte[4];
				this.content.get(dataLengthIndicatorBytes);
				int dataLengthIndicator = SyncSafeInt.toInt(dataLengthIndicatorBytes);
				this.headerFlags.get("dataLengthIndicator").setData(dataLengthIndicator);
			}
		}
	}
	
	public class FrameHeaderFlag extends Flag {
		Integer data;
		
		public FrameHeaderFlag(String description, boolean isSet) {
			super(description, isSet);
			this.data = null;
		}
		
		public void setData(int data) {
			this.data = data;
		}
		
		public boolean hasData() {
			return (this.data == null);
		}
	}
}