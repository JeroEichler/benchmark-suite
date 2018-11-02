package bsuite.step2.graphretrieval;

import java.util.List;

import org.apache.jena.rdf.model.Model;

import bsuite.model.entity.Entity;
import bsuite.model.entity.EntityList;
import bsuite.model.entity.Genre;
import bsuite.utils.io.EntityIO;
import bsuite.utils.io.FoldersNFiles;
import bsuite.utils.io.ModelIO;


public class CrawlingViewer {

	public static void main(String[] args) {
		int movieCounter = 0;
		long start = System.currentTimeMillis();
		
		List<String> genres = Genre.genres();
		
		for(String genre : genres) {
			EntityList entityList = EntityIO.readEntityList(FoldersNFiles.graphFolder + "//" + genre , genre); 
			for(Entity entity : entityList.entities) {
				Model model = EntityIO.loadGraph(FoldersNFiles.graphFolder, genre, entity.normalizedTitle);
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
