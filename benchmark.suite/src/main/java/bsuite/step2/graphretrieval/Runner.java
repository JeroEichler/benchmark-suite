package bsuite.step2.graphretrieval;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

import bsuite.base.crawling.Crawler;
import bsuite.model.entity.Entity;
import bsuite.model.entity.EntityList;
import bsuite.model.entity.Genre;
import bsuite.utils.Config;
import bsuite.utils.io.BasicIO;
import bsuite.utils.io.EntityIO;
import bsuite.utils.io.FoldersNFiles;

public class Runner {

	public static void main(String[] args) {
		
		List<String> genres = Genre.genres();
		
		long start = System.currentTimeMillis();
		
		for(String genre : genres) {
			long startB = System.currentTimeMillis();
			
			readEntitiesOf(genre);
			
			long elapsedTimeB = System.currentTimeMillis() - startB;
			
			System.out.println("### "+ genre +"Finished at "+elapsedTimeB/1000F+" seconds");
			
			try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		long elapsedTime = System.currentTimeMillis() - start;
		
		System.out.println("### Finished at "+elapsedTime/1000F+" seconds");
	}
	
	public static void readEntitiesOf(String genre) {
		int savedConter=0;
		int ignoredConter=0;
		EntityList entityList = null;
		EntityList crawledList = new EntityList(genre);
		EntityList problemList = new EntityList(genre);
		
		entityList = EntityIO.readEntityList(FoldersNFiles.uriMapFolder, genre); 		
		
		for(Entity entity : entityList.entities) {				
			if(entity.DBpediaURI.equals("NotFound") || entity.ImdbURI.equals("NotFound") ){
				//do nothing.
				ignoredConter++;
				System.out.println("--------------------------> nope "+ignoredConter);
			}
			else{
				Crawler crawler = new Crawler(Config.RemoteEndpoint);
				Model model = crawler.search(entity.ImdbURI);
				if(problemOccured(model)) {
					ignoredConter++;
					System.out.println("--------------------------> failed "+ignoredConter);
					crawledList.entities.add(entity);
					problemList.entities.add(entity);
				}
				else {
					EntityIO.saveGraph(FoldersNFiles.graphFolder, genre, entity.normalizedTitle, model);
					crawledList.entities.add(entity);
					savedConter++;
					System.out.println("gone "+savedConter);
				}
				
				

				try {
					
					TimeUnit.SECONDS.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		String progress = 
				" | Genre: "+		genre+
				" | Processed: "+		savedConter+
				" | Ignored: "+		ignoredConter;

		System.out.println(progress);
		
		BasicIO.saveEntity(FoldersNFiles.graphFolder + "//" + genre, "__" + genre, crawledList);
		
		if(problemList.entities.size() > 0) {
			EntityIO.saveProgress(FoldersNFiles.graphFolder, genre, problemList, false);
		}
		
	}

	
	private static boolean problemOccured(Model model) {
		Resource test2 = ResourceFactory.createResource("Query Problem");		
		boolean result = model.containsResource(test2);
		return result;
	}

}
