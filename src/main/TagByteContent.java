package main;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import util.SyncSafeInt;

import java.io.RandomAccessFile;

public class TagByteContent {
	Header tagHeader;
	ByteBuffer content;
	int size;
	
	// Works on ID3v2.x
	public TagByteContent(File file) {
		int HEADER_LENGTH = 10;

		byte[] header = new byte[HEADER_LENGTH];
		try {
			RandomAccessFile raf = new RandomAccessFile(file, "r");
			raf.read(header);
		} catch (IOException e) {
			System.exit(1); // TODO: find better way to end program
		}
		
		ByteBuffer bbHeader = ByteBuffer.wrap(header);
		
		// Tag header
		byte[] ID3TagBytes = new byte[3];
		bbHeader.get(ID3TagBytes);
		String ID3Tag = new String(ID3TagBytes);
		
		int version = (int) bbHeader.get();
		int revision = (int) bbHeader.get();
		
		int flags = (int) bbHeader.get(); // char is 2 bytes
		
		byte[] sizeBytes = new byte[4];
		bbHeader.get(sizeBytes);
		int size = SyncSafeInt.toInt(sizeBytes);
		
		this.tagHeader = new Header(ID3Tag, version, revision, flags, size);
		this.size = this.tagHeader.size;
		
		// read size bytes into content
		byte[] contentBytes = new byte[this.size];
		try {
			RandomAccessFile raf = new RandomAccessFile(file, "r");
			raf.skipBytes(HEADER_LENGTH);
			raf.read(contentBytes);
		} catch (IOException e) {
			System.exit(1);
		}
		this.content = ByteBuffer.wrap(contentBytes);
	}
	
	public Header getHeader() {
		return this.tagHeader;
	}
	
	public ExtendedHeader getExtendedHeader() {
		byte[] ehSizeBytes = new byte[4];
		this.content.get(ehSizeBytes);
		int ehSize = SyncSafeInt.toInt(ehSizeBytes);
		
		int ehNumOfFlagBytes = (int) this.content.get();
		int ehFlags = (int) this.content.get();
		
		byte[] ehContent = new byte[ehSize];
		this.content.get(ehContent);
		
		ExtendedHeader eh = new ExtendedHeader(ehSize, ehNumOfFlagBytes, ehFlags, ehContent);
		return eh;
	}
	
	public boolean hasNextFrame() {
		return false;
	}
	
	public Frame getNextFrame() {
		return null;
	}
	
	public Footer getFooter() {
		return null;
	}
	
	public Padding getPadding() {
		return null;
	}
}
