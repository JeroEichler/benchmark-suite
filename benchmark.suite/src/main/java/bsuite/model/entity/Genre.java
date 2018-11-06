package bsuite.model.entity;

import java.util.Arrays;
import java.util.List;

import bsuite.utils.io.BasicIO;
import bsuite.utils.io.FoldersNFiles;

public class Genre {
	
	
	
	public static List<String> genres(){
		List<String> genres = null;
		
		genres = BasicIO.readList(FoldersNFiles.inputFolder, FoldersNFiles.genresFile);
		
//		genres = Arrays.asList(/*genresX[1], genresX[5], genresX[8],*/
//				genresX[9]/*, genresX[10]*/ );
		
		return genres;
	}
	
	private static final String[] genresX = {
			"action movies",  			// 0
			"adventure movies",  		// 1
			"animation movies",  		// 2
			"comedy movies",  			// 3
			"documentary movies",  		// 4
			"drama movies",  			// 5
			"fantasy movies",  			// 6
			"horror movies",  			// 7
			"musical movies",  			// 8
			"romance movies",  			// 9
			"romantic comedy movies",  	//10 
			"science fiction movies",  	//11
			"thriller movies",  		//12
			"war movies",  				//13
			"western movies"			//14
			};
}
