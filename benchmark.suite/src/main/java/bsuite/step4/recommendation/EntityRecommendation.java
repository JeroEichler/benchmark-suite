package bsuite.step4.recommendation;

import java.util.ArrayList;
import java.util.List;

import bsuite.model.Entity;
import bsuite.model.EntityPairScore;
import bsuite.model.EntityScore;

public class EntityRecommendation {

	public Entity base;
	
	public List<EntityScore> suggestions;
	
	public EntityRecommendation(Entity entity) {
		this.base = entity;
		this.suggestions = new ArrayList<EntityScore>();
	}
	
	public EntityRecommendation() {
		this.suggestions = new ArrayList<EntityScore>();
	}
	
	public void addSuggestion(List<EntityPairScore> list) {
		List<EntityPairScore> pairs = limitList(list);
		for(EntityPairScore pair : pairs) {
			EntityScore m = new EntityScore(pair.candidate, pair.score);
			suggestions.add(m);
		}
	}
	
	private List<EntityPairScore> limitList(List<EntityPairScore> list){
		if(list.size() < 3) {
			return list;
		}
		else {
			return list.subList(0, 3);
		}
	}

}
