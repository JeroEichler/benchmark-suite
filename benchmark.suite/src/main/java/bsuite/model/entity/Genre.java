package bsuite.model.entity;

import java.util.Arrays;
import java.util.List;

import bsuite.utils.io.BasicIO;
import bsuite.utils.io.FoldersNFiles;

public class Genre {
	
	public static List<String> genres(){
		List<String> genres = BasicIO.readList(FoldersNFiles.inputFolder, FoldersNFiles.genresFile);
		
//		genres = Arrays.asList(FoldersNFiles.genresX[12]/*, FoldersNFiles.genresX[13], FoldersNFiles.genresX[14]*/);
		
		return genres;
	}

}
