package id3v2.main;

// TODO: This class may be unnecessary
public class Padding {
	int length;
	
	public Padding(byte[] paddingBytes) {
		try {
			for (int i = 0; i < paddingBytes.length; i++) {
				if (paddingBytes[i] != 0) {
					throw new Exception();
				}
			}
		}
		catch (Exception e) {
			System.exit(1);
		}
		
		this.length = paddingBytes.length;
	}
}
