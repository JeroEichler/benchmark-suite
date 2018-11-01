package bsuite.model;

import java.util.Arrays;
import java.util.List;

import bsuite.io.BasicIO;
import bsuite.utils.FoldersNFiles;

public class GenreLoader {
	
	public static List<String> genres(){
//		List<String> genres = BasicIO.readList(FoldersNFiles.inputFolder, FoldersNFiles.genresFile);
		
		List<String> genres = Arrays.asList(FoldersNFiles.genresX[12]/*, FoldersNFiles.genresX[13], FoldersNFiles.genresX[14]*/);
		
		return genres;
	}

}
