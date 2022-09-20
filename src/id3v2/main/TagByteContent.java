package id3v2.main;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import util.SyncSafeInt;
import v23.ExtendedHeaderV23;
import v24.ExtendedHeaderV24;

import java.io.RandomAccessFile;

public class TagByteContent {
	int HEADER_LENGTH = 10;
	
	Header tagHeader;
	ByteBuffer content;
	int size;
	
	// Works on ID3v2.x
	public TagByteContent(File file) {

		byte[] header = new byte[HEADER_LENGTH];
		try {
			RandomAccessFile raf = new RandomAccessFile(file, "r");
			raf.read(header);
			raf.close();
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
		
		int flags = (int) bbHeader.get();
		
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
			raf.close();
		} catch (IOException e) {
			System.exit(1);
		}
		this.content = ByteBuffer.wrap(contentBytes);
	}
	
	public Header getHeader() {
		return this.tagHeader;
	}
	
	public ExtendedHeaderV23 getExtendedHeaderV23() {
		byte[] ehSizeBytes = new byte[4];
		this.content.get(ehSizeBytes);
		int ehSize = Integer.parseInt(new String(ehSizeBytes));
		
		int ehFlags = (int) this.content.get();
		this.content.get(); // last flag byte holds no data
		
		byte[] ehPaddingSizeBytes = new byte[4];
		this.content.get(ehPaddingSizeBytes);
		int ehPaddingSize = Integer.parseInt(new String(ehPaddingSizeBytes));
		
		byte[] ehContent = new byte[ehSize - 6]; // minus flag and padding bytes
		this.content.get(ehContent);
		
		ExtendedHeaderV23 eh = new ExtendedHeaderV23(ehSize, ehFlags, ehPaddingSize, ehContent);
		return eh;
	}
	
	public ExtendedHeaderV24 getExtendedHeaderV24() {
		byte[] ehSizeBytes = new byte[4];
		this.content.get(ehSizeBytes);
		int ehSize = SyncSafeInt.toInt(ehSizeBytes);
		
		int ehNumOfFlagBytes = (int) this.content.get();
		int ehFlags = (int) this.content.get();
		
		byte[] ehContent = new byte[ehSize - 6];
		this.content.get(ehContent);
		
		ExtendedHeaderV24 eh = new ExtendedHeaderV24(ehSize, ehNumOfFlagBytes, ehFlags, ehContent);
		return eh;
	}
	
	public boolean hasNextFrame() {
		if (this.content.position() > this.size) {
			return false;
		}
		// TODO: Another check for frame tag
		return true;
	}
	
	public Frame getNextFrame() {
		byte[] frameTitleBytes = new byte[4];
		byte[] frameSizeBytes = new byte[4];
		byte[] frameFlagBytes = new byte[2];
		this.content.get(frameTitleBytes);
		this.content.get(frameSizeBytes);
		this.content.get(frameFlagBytes);
		
		String frameTitle = new String(frameTitleBytes);
		
		int frameSize = SyncSafeInt.toInt(frameSizeBytes);
		byte[] frameContentBytes = new byte[frameSize];
		
		return FrameFactory.makeFrame(frameTitle, frameSize, frameFlagBytes, frameContentBytes);
	}
	
	public Footer getFooter() {
		byte[] ThreeDITagBytes = new byte[3];
		this.content.get(ThreeDITagBytes);
		String ThreeDITag = new String(ThreeDITagBytes);
		
		int version = (int) this.content.get();
		int revision = (int) this.content.get();
		
		int flags = (int) this.content.get(); // char is 2 bytes
		
		byte[] sizeBytes = new byte[4];
		this.content.get(sizeBytes);
		int size = SyncSafeInt.toInt(sizeBytes);
		
		return new Footer(ThreeDITag, version, revision, flags, size);
	}
	
	public Padding getPadding() {
		byte[] paddingBytes = new byte[this.size - this.content.position()];
		this.content.get(paddingBytes);
		return new Padding(paddingBytes);
	}
}
