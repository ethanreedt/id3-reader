package id3v2.frames;

import id3v2.main.AbstractFrame;

public class SynchronisedTempoCodesFrame extends AbstractFrame {
	int timeStampFormat; // TODO: Change location of translateTimeStampFormat from EventTimingCodeFrame
	byte[] tempoData;
	
	public SynchronisedTempoCodesFrame(String title, int size, byte[] flags, byte[] content, int version) {
		super(title, size, flags, content, version);
		this.timeStampFormat = this.content.get();
		this.tempoData = new byte[this.content.remaining()];
		this.content.get(this.tempoData);
	}

}
