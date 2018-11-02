package bsuite.model.entity;

public class EntityScore extends Entity {
	
	public double score;
	
	public EntityScore(Entity m, double s) {
		this.DBpediaURI = m.DBpediaURI;
		this.genre = m.genre;
		this.ImdbURI = m.ImdbURI;
		this.normalizedTitle = m.normalizedTitle;
		this.title = m.title;
		this.year = m.year;
		this.score = s;
	}
	
	public EntityScore() {
		
	}

}
