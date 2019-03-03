package bsuite.step3.datasetanalysis;

import java.util.List;

import bsuite.model.entity.Entity;
import bsuite.model.entity.EntityList;
import bsuite.step3.EntityLoader;
import bsuite.utils.io.BasicIO;
import bsuite.utils.io.FoldersNFiles;


public class Runner {

	public static void main(String[] args) {
		List<EntityList> lists = EntityLoader.loadAllEntitiesWithModel();
		
		DatasetAnalyzer analyzer = new DatasetAnalyzer();
		
		for(EntityList entityList : lists) {
			for(Entity entity : entityList.entities) {
				analyzer.process(entity);
			}
		}
		
		analyzer.print();
		
		List<String> ignoredProperties = analyzer.extractUnbalancedProperties();
		manualSetUp(ignoredProperties);
		
		System.out.println("##########################################");
		
		for(String prop : ignoredProperties) {
			System.out.println(prop);
		}
		
		BasicIO.saveEntity(FoldersNFiles.ignPropFolder, FoldersNFiles.ignPropFile, ignoredProperties);
	}
	
	private static void manualSetUp(List<String> ignoredProperties) {
		ignoredProperties.add("http://data.linkedmdb.org/resource/movie/genre");
	}

}
