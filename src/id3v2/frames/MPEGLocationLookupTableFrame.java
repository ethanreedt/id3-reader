package id3v2.frames;

import java.util.BitSet;

import id3v2.main.AbstractFrame;

// TODO: FIX
public class MPEGLocationLookupTableFrame extends AbstractFrame {
	byte[] MPEGFramesBetweenReference;
	byte[] bytesBetweenReference;
	byte[] millisecondsBetweenReference;
	int bitsForBytesDeviation;
	int bitsForMillisecondsDeviation;
	// TODO: Fix storing as BitSet
	BitSet[] referenceByteDeviations;
	BitSet[] referenceMillisecondDeviations;
	
	public MPEGLocationLookupTableFrame(String title, int size, byte[] flags, byte[] content, int version) {
		super(title, size, flags, content, version);
		this.MPEGFramesBetweenReference = new byte[2];
		this.content.get(this.MPEGFramesBetweenReference);
		this.bytesBetweenReference = new byte[3];
		this.content.get(this.bytesBetweenReference);
		this.millisecondsBetweenReference = new byte[3];
		this.content.get(this.millisecondsBetweenReference);
		this.bitsForBytesDeviation = this.content.get();
		this.bitsForMillisecondsDeviation = this.content.get();
		
		// TODO: make sure denominator is not zero
		int referenceDataSize = this.bitsForBytesDeviation + this.bitsForMillisecondsDeviation;
		int numOfReferences = this.content.remaining() / referenceDataSize;
		this.referenceByteDeviations = new BitSet[numOfReferences];
		this.referenceMillisecondDeviations = new BitSet[numOfReferences];
		
		// TODO: check that `referenceDataSize` is divisible by 4
		
		// TODO: Unsure if this is the correct implementation
		byte[] remainingBytes = new byte[this.content.remaining()];
		this.content.get(remainingBytes);
		
		BitSet bits = BitSet.valueOf(remainingBytes);
		
		int currentReference = 0;
		while (currentReference < numOfReferences) {
			int bitLocation = (currentReference * referenceDataSize);
			this.referenceByteDeviations[currentReference] 
					= bits.get(bitLocation, bitLocation + this.bitsForBytesDeviation);
			this.referenceMillisecondDeviations[currentReference]
					= bits.get(bitLocation + this.bitsForBytesDeviation, bitLocation + this.bitsForBytesDeviation + this.bitsForMillisecondsDeviation);
		}
	}
	
}
