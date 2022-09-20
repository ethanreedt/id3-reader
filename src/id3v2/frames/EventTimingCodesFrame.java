package id3v2.frames;

import java.util.HashMap;

import id3v2.main.AbstractFrame;

public class EventTimingCodesFrame extends AbstractFrame {
	int timeStampFormat;
	HashMap<Integer, byte[]> eventTimeStamps;

	public EventTimingCodesFrame(String title, int size, byte[] flags, byte[] content, int version) {
		super(title, size, flags, content, version);
		this.timeStampFormat = this.content.get();
		while (this.content.hasRemaining()) {
			int eventType = this.content.get();
			byte[] eventTimeStamp = new byte[4]; // TODO: Confusion in documentation "$xx (xx ...)" ?
			this.content.get(eventTimeStamp);
			this.eventTimeStamps.put(eventType, eventTimeStamp);
		}
	}
	
	// TODO: There has to be a better way of doing this...
	public String translateTimeStampFormat(int timeStampFormat) {
		HashMap<Integer, String> lookup = new HashMap<Integer, String>();
		lookup.put(0, "padding (has no meaning)");
		lookup.put(1, "end of initial silence");
		lookup.put(2, "intro start");
		lookup.put(3, "main part start");
		lookup.put(4, "outro start");
		lookup.put(5, "outro end");
		lookup.put(6, "verse start");
		lookup.put(7, "refrain start");
		lookup.put(8, "interlude start");
		lookup.put(9, "theme start");
		lookup.put(10, "variation start");
		lookup.put(11, "key change");
		lookup.put(12, "time change");
		lookup.put(13, "momentary unwanted nosie (Snap, Crackle & Pop)");
		lookup.put(14, "sustained noise");
		lookup.put(15, "sustained noise end");
		lookup.put(16, "intro end");
		lookup.put(17, "main part end");
		lookup.put(18, "verse end");
		lookup.put(19, "refrain end");
		lookup.put(20, "theme end");
		lookup.put(21, "profanity"); // TODO: Note: V24
		lookup.put(22, "profanity end"); // TODO: Note: V24
		lookup.put(253, "audio end (start of silence)");
		lookup.put(254, "audio file ends");
		lookup.put(255, "one more byte of events follows (all the following bytes with the value $FF have the same function)"); // TODO: May cause issue with unsynchronisation in V24
		return lookup.get(timeStampFormat);
	}
}
