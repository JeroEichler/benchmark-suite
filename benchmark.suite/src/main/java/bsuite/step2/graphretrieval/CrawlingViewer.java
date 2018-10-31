package bsuite.step2.graphretrieval;

import java.util.Arrays;
import java.util.List;

import org.apache.jena.rdf.model.Model;

import bsuite.io.EntityIO;
import bsuite.io.ModelIO;
import bsuite.model.Entity;
import bsuite.model.EntityList;
import bsuite.utils.FoldersNFiles;


public class CrawlingViewer {

	public static void main(String[] args) {
		int movieCounter = 0;
		long start = System.currentTimeMillis();
		
		List<String> genres = Arrays.asList("adventure movies");
		
		for(String genre : genres) {
			EntityList entityList = EntityIO.readEntityList(FoldersNFiles.graphFolder + "//" + genre , genre); 
			for(Entity entity : entityList.entities) {
				Model model = ModelIO.loadModel(genre, entity.normalizedTitle);
				movieCounter++;
				if(entity.ImdbURI.contains("film/38196")) {
					ModelIO.printModel(model);
				}
				System.out.println("###############" + entity.ImdbURI + "########################");
			}
			
		}
		
		long elapsedTime = System.currentTimeMillis() - start;
		
		System.out.println("### Finished at "+elapsedTime/1000F+" seconds");
		

		System.out.println("### Total "+movieCounter+" movies");

	}
	
}
