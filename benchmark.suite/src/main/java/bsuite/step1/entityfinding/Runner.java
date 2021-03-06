package bsuite.step1.entityfinding;

import java.util.List;

import bsuite.model.entity.Entity;
import bsuite.model.entity.EntityList;
import bsuite.model.entity.Genre;
import bsuite.model.rdf.Constants;
import bsuite.utils.StringFormatter;
import bsuite.utils.io.BasicIO;
import bsuite.utils.io.EntityIO;
import bsuite.utils.io.FoldersNFiles;

public class Runner {

	private static int genreCounter = 0;
	static int entityCounter = 0;
	static int DBpediaURINotFoundCounter = 0;
	static int linkedMDBURINotFoundCounter = 0;

	public static void main(String[] args) {

		List<String> genres = Genre.genres();
		
		for(String genre : genres) {
			try {
				genreCounter ++;
				findURIsOf(genre);
			}
			catch(Exception e) {
			    e.printStackTrace(); 
			    System.exit(1);
			}
		}
		
	}
	
	public static void findURIsOf(String genre) {
		
		EntityList entityList = EntityIO.readEntityList(FoldersNFiles.inputFolder, genre); 		

		EntityList problemList = new EntityList(genre);

		EntityList outputList = new EntityList(genre);
			
		for(Entity entity : entityList.entities) {
			entity.normalizedTitle = StringFormatter.clean(entity.title);
			entityCounter++;
			entity.DBpediaURI = UriLookUp.getUriFromDBpedia(entity.title, entity.year);
//				System.out.println(movie.title+" | "+movie.DBpediaURI);
			
			entity.ImdbURI = UriLookUp.getUriFromLinkedmdbURI(entity.title, entity.year);
//				System.out.println(movie.title+" | "+movie.ImdbURI);
			
			if(entity.DBpediaURI.equals(Constants.ProblemOccured) || entity.ImdbURI.equals(Constants.ProblemOccured)) {
				problemList.entities.add(entity);
			}
			else {
				if(entity.DBpediaURI.equals(Constants.NotFound)) {
					DBpediaURINotFoundCounter++;
//					System.out.println(movie.title+" | "+movie.DBpediaURI);
				}
				if(entity.ImdbURI.equals(Constants.NotFound)) {
					linkedMDBURINotFoundCounter++;
				}
				outputList.entities.add(entity);
			}
			
			
		}		
		
		String progress = genre+
				" | Genre: "+		genreCounter+
				" | Total: "+		entityCounter+
				" | DB: "+		DBpediaURINotFoundCounter+
				" | MDB: "+	linkedMDBURINotFoundCounter;
		
		System.out.println(progress);
		
		BasicIO.saveEntity(FoldersNFiles.uriMapFolder, genre, outputList);
		
		if(problemList.entities.size() > 0) {
			EntityIO.saveProgress(FoldersNFiles.uriMapFolder, genre, problemList, false);
		}
		
	}
}