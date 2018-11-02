package bsuite.io;

public class FoldersNFiles {
	
	public static final String root =			"D://benchmark//";	

	public static final String genresFile =		"__genres";

	public static final String inputFolder =	"0_Input";

	public static final String uriMapFolder =	"1_UriMapping";

	public static final String graphFolder =	"2_DataGraph";

	public static final String pairFolder =		"3_Pair";

	public static final String ignPropFolder =	"3_IgnoredProperties";

	public static final String ignPropFile = 	"__IgnoredProperties";

	public static final String listFolder =		pairFolder + "//List";

	public static final String pathFolder =		pairFolder + "//Path";

	public static final String scoreFolder =	pairFolder + "//Score";

	public static final String recommFolder =	"4_Recommendation";
	
	
	public static final String[] genresX = {
			"action movies",  
			"adventure movies",  
			"animation movies",  
			"comedy movies",  
			"documentary movies",  
			"drama movies",  
			"fantasy movies",  
			"horror movies",  
			"musical movies",  
			"romance movies",  
			"romantic comedy movies",  
			"science fiction movies",  
			"thriller movies",  
			"war movies",  
			"western movies"
			};

	public static final String pathPrefix = "Path_";

	public static final String scorePrefix = "Score_";

	public static final String listPrefix = "_candidatesFor";

	public static final String recommPrefix = "R_";

}
