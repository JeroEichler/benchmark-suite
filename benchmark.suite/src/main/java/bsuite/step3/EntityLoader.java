package bsuite.step3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.jena.rdf.model.Model;

import bsuite.model.entity.Entity;
import bsuite.model.entity.EntityList;
import bsuite.model.entity.Genre;
import bsuite.utils.io.EntityIO;
import bsuite.utils.io.FoldersNFiles;

public class EntityLoader {
	
	public static List<EntityList> loadAllEntitiesWithModel() {
		
		List<String> genres = Genre.genres();
		
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
	
	public static Map<String,List<String>> buildMap(List<EntityList> entityLists) {
		
		Map<String,List<String>> map = new ConcurrentHashMap<String,List<String>>();

		for(EntityList list : entityLists) {
			for(Entity entity : list.entities) {				
				if(map.containsKey(entity.normalizedTitle)) {
					List<String> g = map.get(entity.normalizedTitle);
					if(!g.contains(list.genre)) {
						g.add(list.genre);
						map.put(entity.normalizedTitle, g);
					}
				}
				else {
					List<String> g = new ArrayList<String>();
					g.add(list.genre);
					map.put(entity.normalizedTitle, g);
				}
				
			}			
		}
		
		return map;		
	}

}
