package bsuite.step3.datasetanalysis;

import java.util.List;
import java.util.Map;

import bsuite.io.BasicIO;
import bsuite.io.FoldersNFiles;
import bsuite.model.Entity;
import bsuite.model.EntityList;
import bsuite.step3.EntityLoader;


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
		
		System.out.println("##########################################");
		
		for(String prop : ignoredProperties) {
			System.out.println(prop);
		}
		
		BasicIO.saveEntity(FoldersNFiles.ignPropFolder, FoldersNFiles.ignPropFile, ignoredProperties);
	}

}
