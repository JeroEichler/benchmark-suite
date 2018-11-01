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
	
	public static List<EntityList> loadAllEntitiesWithModel() {
		
		List<String> genres = GenreLoader.genres();
		
		List<EntityList> entityLists = new ArrayList<EntityList>();

		for(String genre : genres) {
			EntityList list = EntityIO.readEntityList(FoldersNFiles.graphFolder + "//" + genre , "__" + genre); 
			for(Entity entity : list.entities) {
				Model model = EntityIO.loadGraph(FoldersNFiles.graphFolder, genre, entity.normalizedTitle);
				entity.model = model;
				entity.genre = genre;
			}			
			entityLists.add(list);
		}
		
		return entityLists;		
	}
	
	public static Map<String,List<String>> buildDictionary(List<EntityList> entityLists) {
		
		Map<String,List<String>> dictionary = new ConcurrentHashMap<String,List<String>>();

		for(EntityList list : entityLists) {
			for(Entity entity : list.entities) {				
				if(dictionary.containsKey(entity.normalizedTitle)) {
					List<String> g = dictionary.get(entity.normalizedTitle);
					if(!g.contains(list.genre)) {
						g.add(list.genre);
						dictionary.put(entity.normalizedTitle, g);
					}
				}
				else {
					List<String> g = new ArrayList<String>();
					g.add(list.genre);
					dictionary.put(entity.normalizedTitle, g);
				}
				
			}			
		}
		
		return dictionary;		
	}

}
