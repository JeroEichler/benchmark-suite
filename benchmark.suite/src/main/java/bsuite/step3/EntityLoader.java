package bsuite.step3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.jena.rdf.model.Model;

import bsuite.io.EntityIO;
import bsuite.io.ModelIO;
import bsuite.model.Entity;
import bsuite.model.EntityList;
import bsuite.model.GenreLoader;
import bsuite.utils.FoldersNFiles;

public class EntityLoader {
	
	public static List<EntityList> loadMoviesPerGenre(Map<String,List<String>> dictionary) {
		
		List<String> genres = GenreLoader.genres();
		
		dictionary = new ConcurrentHashMap<String,List<String>>();
		
		List<EntityList> entityLists = new ArrayList<EntityList>();

		for(String genre : genres) {
			EntityList list = EntityIO.readEntityList(FoldersNFiles.graphFolder + "//" + genre , "__" + genre); 
			for(Entity entity : list.entities) {
				Model model = EntityIO.loadGraph(FoldersNFiles.graphFolder, genre, entity.normalizedTitle);
				entity.model = model;
				entity.genre = genre;
				
				if(dictionary.containsKey(entity.normalizedTitle)) {
					List<String> g = dictionary.get(entity.normalizedTitle);
					if(!g.contains(genre)) {
						g.add(genre);
						dictionary.put(entity.normalizedTitle, g);
					}
				}
				else {
					List<String> g = new ArrayList<String>();
					g.add(genre);
					dictionary.put(entity.normalizedTitle, g);
				}
				
			}			
			entityLists.add(list);
		}
		
		return entityLists;		
	}

}
