package bsuite.model.entity;

import java.util.ArrayList;
import java.util.List;

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
	
	public void addSuggestion(List<EntityPairScore> pairs) {
		for(EntityPairScore pair : pairs) {
			EntityScore m = new EntityScore(pair.candidate, pair.score);
			suggestions.add(m);
		}
	}

}
