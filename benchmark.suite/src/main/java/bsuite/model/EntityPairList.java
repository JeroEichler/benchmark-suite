package bsuite.model;

import java.util.ArrayList;
import java.util.List;

public class EntityPairList {
	
	public Entity base;
	
	public List<Entity> entities;
	
	public EntityPairList(Entity entity) {
		this.base = entity;
		this.entities = new ArrayList<Entity>();
	}
	public EntityPairList() {
	}
	
	public boolean contains(Entity entity) {
		for(Entity m : entities) {
			if(m.title.equals(entity.title)) {
				return true;
			}
		}
		return false;
	}
	
	

}
