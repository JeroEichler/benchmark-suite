package bsuite.model;

import org.apache.jena.rdf.model.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Entity {
	
	public Entity(String title, int year) {
		this.title = title;
		this.year = year;
	}
	
	public Entity() {
		
	}
	
	public String title;

	public String normalizedTitle;
	
	public int year;
	
	public String DBpediaURI;
	
	public String ImdbURI;
	
	@JsonIgnore
	public Model model;
	

	@JsonIgnore
	public String genre;

}
