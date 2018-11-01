package bsuite.step3.datasetanalysis;

import java.util.List;
import java.util.Map;

import bsuite.io.BasicIO;
import bsuite.model.Entity;
import bsuite.model.EntityList;
import bsuite.step3.EntityLoader;
import bsuite.utils.FoldersNFiles;


public class Runner {

	static Map<String,List<String>> dictionary;

	public static void main(String[] args) {
		List<EntityList> lists = EntityLoader.loadMoviesPerGenre(dictionary);
		
		DatasetAnalyzer analyzer = new DatasetAnalyzer();
		
		for(EntityList entityList : lists) {
			for(Entity entity : entityList.entities) {
				analyzer.process(entity);
			}
		}
		
		analyzer.print();
		
		List<String> ignoredProperties = analyzer.extractUnbalancedProperties();
		
		System.out.println("##########################################");
		
		for(String prop : ignoredProperties) {
			System.out.println(prop);
		}
		
		BasicIO.saveEntity(FoldersNFiles.ignPropFolder, FoldersNFiles.ignPropFile, ignoredProperties);
	}

}
