package id3v2.main;

import java.nio.ByteBuffer;

import util.SyncSafeInt;

public class FrameHeader {
	String id;
	int size;
	int flags;
	
	public FrameHeader(byte[] headerBytes) {
		byte[] idBytes = {headerBytes[0], headerBytes[1], headerBytes[2], headerBytes[3]};
		this.id = new String(idBytes);
		byte[] sizeBytes = {headerBytes[4], headerBytes[5], headerBytes[6], headerBytes[7]};
		this.size = SyncSafeInt.toInt(sizeBytes);
		byte[] flagBytes = {headerBytes[8], headerBytes[9]};
		this.flags = ByteBuffer.wrap(flagBytes).getInt();
	}
}
