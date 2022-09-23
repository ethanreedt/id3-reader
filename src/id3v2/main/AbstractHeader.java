package id3v2.main;

import java.util.HashMap;

import id3v2.util.SyncSafeInt;

public abstract class AbstractHeader {
	public static int HEADER_SIZE = 10;
	
	String identifier;
	int version;
	int revision;
	protected HashMap<String, HeaderFlag> flags;
	int size;
	
	protected AbstractHeader(String identifier, int version, int revision, int flags, int size) throws IllegalArgumentException {
		this.identifier = identifier;
		setVersion(version);
		setRevision(revision);
		this.flags = new HashMap<String, HeaderFlag>();
		setFlags(flags);
		this.size = size;
	}
	
	protected AbstractHeader(byte[] headerBytes) throws IllegalArgumentException {
		byte[] idBytes = {headerBytes[0], headerBytes[1], headerBytes[2]};
		this.identifier = new String(idBytes);
		
		setVersion((int) headerBytes[3]);
		setVersion((int) headerBytes[4]);
		
		this.flags = new HashMap<String, HeaderFlag>();
		setFlags((int) headerBytes[5]);
		
		byte[] sizeBytes = {headerBytes[6], headerBytes[7], headerBytes[8], headerBytes[9]};
		this.size = SyncSafeInt.toInt(sizeBytes);
	}
	
	private void setVersion(int version) throws IllegalArgumentException {
		if (Integer.parseInt("FF", 16) == version) {
			throw new IllegalArgumentException();
		}
		this.version = version;
	}
	
	private void setRevision(int revision) throws IllegalArgumentException {
		if (Integer.parseInt("FF", 16) == revision) {
			throw new IllegalArgumentException();
		}
		this.revision = revision;
	}
	
	protected abstract void setFlags(int flags) throws IllegalArgumentException;
	
	public HashMap<String, HeaderFlag> getFlags() {
		return this.flags;
	}
	
	public class HeaderFlag extends Flag {
		public HeaderFlag(String description, boolean isSet) {
			super(description, isSet);
		}
	}
}
