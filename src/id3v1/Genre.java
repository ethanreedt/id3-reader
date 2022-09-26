package id3v1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Genre {
	private static HashMap<Integer, String> ID3v1GenreMap = null;
	private static HashMap<Integer, String> ID3v1WinampGenreMap = null;
			
	private static HashMap<Integer, String> genresFromCSV(String filepath) throws IOException {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		BufferedReader br = new BufferedReader(new FileReader(filepath));
		String line = "";
		while ((line = br.readLine()) != null) {
			String[] row = line.split(",");
			map.put(Integer.parseInt(row[0]), row[1]);
		}
		br.close();
		return map;
	}
	
	private static HashMap<Integer, String> getID3v1GenreMap() throws IOException {
		if (ID3v1GenreMap == null) {
			// TODO: Share Genre resources globally
			Genre.ID3v1GenreMap = genresFromCSV("resources/ID3v1Genre.csv");
		}
		return ID3v1GenreMap;
	}
	
	private static HashMap<Integer, String> getID3v1WinampGenreMap() throws IOException {
		if (ID3v1WinampGenreMap == null) {
			Genre.ID3v1WinampGenreMap = genresFromCSV("resources/ID3v1WinampGenre.csv");
		}
		return ID3v1WinampGenreMap;
	}
	
	public static String getID3v1Genre(byte genreByte) {
		String genre = "";
		try {
			genre = getID3v1GenreMap().get((int) genreByte);
		} catch (IOException e) {}
		if (genre == "") {
			// TODO: determine if Winamp should be specified for 
			// this information to be retrieved
			try {
				genre = getID3v1WinampGenreMap().get((int) genreByte);
			} catch (IOException e) {}
		}
		return genre;
	}
}
