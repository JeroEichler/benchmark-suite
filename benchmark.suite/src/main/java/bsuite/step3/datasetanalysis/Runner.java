package bsuite.step3.datasetanalysis;

import java.util.List;

import bsuite.io.BasicIO;
import bsuite.model.Entity;
import bsuite.model.EntityList;
import bsuite.utils.FoldersNFiles;


public class Runner {

	public static void main(String[] args) {
		List<EntityList> lists = null; //MoviePairCalculator.loadMoviesPerGenre();
		
		DatasetAnalyzer analyzer = new DatasetAnalyzer();
		
		for(EntityList entityList : lists) {
			for(Entity movie : entityList.entities) {
				analyzer.process(movie);
			}
		}
		
		analyzer.print();
		
		List<String> ignoredProperties = analyzer.extractUnbalancedProperties();
		
		for(String prop : ignoredProperties) {
			System.out.println(prop);
		}
		
		BasicIO.saveEntity(FoldersNFiles.ignPropFolder, FoldersNFiles.ignPropFile, ignoredProperties);
	}

}
