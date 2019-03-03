package bsuite.step4.recommendation;

import java.util.Collections;
import java.util.List;

import bsuite.model.entity.Entity;
import bsuite.model.entity.EntityPairScore;
import bsuite.model.entity.EntityRecommendation;

public class Recommender {
	
	public static EntityRecommendation makeRecommendation(Entity baseEntity, List<EntityPairScore> candidatePairs) {
		Collections.sort(candidatePairs);
		limitList(candidatePairs);
		EntityRecommendation recommendation = new EntityRecommendation(baseEntity);
		recommendation.addSuggestion(candidatePairs);
		
		return recommendation;
	}
	

	
	private static List<EntityPairScore> limitList(List<EntityPairScore> list){
		if(list.size() < 3) {
			return list;
		}
		else {
			return list.subList(0, 3);
		}
	}

}
