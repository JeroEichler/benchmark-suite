package bsuite.model.entity;

import bsuite.step3.pairanalysis.PathAnalyzer;

public class EntityPairScore implements Comparable<EntityPairScore> {

	public Entity base;	
	public Entity candidate;
	public double score;
	
	public EntityPairScore(EntityPairPath pair) {
		base = pair.base;
		candidate = pair.candidate;
		score = PathAnalyzer.analyzePath(pair.paths);
	}
	
	public EntityPairScore() {}

	@Override
	public int compareTo(EntityPairScore arg0) {
		if (this.score > arg0.score) {
	          return -1;
	     }
	     if (this.score < arg0.score) {
	          return 1;
	     }
	     return 0;
	}

}
