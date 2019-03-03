package bsuite.step1.entityfinding;

public class UriLookUp {
	
	public static String getUriFromDBpedia(String title, int year) {
		return DBpediaURIFinder.getUri(title, year);
	}
	
	public static String getUriFromLinkedmdbURI(String title, int year) {
		return LinkedmdbURIFinder.getUri(title, year);
	}

}
