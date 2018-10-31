package bsuite.model;

import java.util.ArrayList;
import java.util.List;

public class EntityList {

	public String genre;
	
	public List<Entity> entities;
	
	public EntityList(String genre) {
		this.genre = genre;
		this.entities = new ArrayList<Entity>();
	}
	public EntityList() {
	}
	
	
	public List<String> getTitles(){
		List<String> response  = new ArrayList<String>();
		for(Entity entity : entities) {
			response.add(entity.title);
		}
		
		return response;
	}
}
