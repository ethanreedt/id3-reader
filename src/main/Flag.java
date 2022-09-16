package main;

public abstract class Flag {
	String description;
	boolean isSet;
	
	public Flag(String description, boolean isSet) {
		this.description = description;
		this.isSet = isSet;
	}
	
	public boolean isSet() {
		return this.isSet;
	}
}
