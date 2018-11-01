package bsuite.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;

public class PropertyStats {
	
	public Property property;
	
	public int propertyCounter;
	
	List<RDFNode> objects;
	
	private Map<RDFNode,Integer> occurrences;
	
	public PropertyStats(Property prop, RDFNode object) {
		this.property = prop;
		this.propertyCounter = 1;
		this.objects = new ArrayList<RDFNode>();
		objects.add(object);
		
		this.occurrences = new ConcurrentHashMap<RDFNode,Integer>();
		this.occurrences.put(object, 1);
	}
	
	public void add(RDFNode object) {
		this.propertyCounter++;
		if(!objects.contains(object)) {
			objects.add(object);
		}
		
		if(occurrences.containsKey(object)) {
			int n = occurrences.get(object);
			n++;
			occurrences.put(object, n);
		}
		else {
			occurrences.put(object, 1);
		}
	}
	

	
	public void printValues() {
		for(RDFNode o : objects){
			int n = occurrences.get(o);
			float reason = (float) n / propertyCounter;
			System.out.println("   ---" +o.toString() +"  "+n+"  "+reason);
		}
	}
	
	public void printHealth() {
		float reason = (float) objects.size() / propertyCounter;
		if( reason < 0.15) {
				System.out.println("~~~" +property.getURI());
		}
	}
	
	public void printValues_v2() {
		if(property.getURI().equals("http://data.linkedmdb.org/resource/movie/country")
				|| property.getURI().equals("http://data.linkedmdb.org/resource/movie/language")
				|| property.getURI().equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#type")) {
			for(RDFNode o : objects){
				int n = occurrences.get(o);
				float reason = (float) n / propertyCounter;
				System.out.println("   ---" +o.toString() +"  "+n+"  "+reason);
			}
		}
	}
	

	
	public void printHealth_v2() {
		for(RDFNode o : objects){
			int n = occurrences.get(o);
			float reason = (float) n / propertyCounter;
			if( reason > 0.75 && n != 1) {
				System.out.println(" # " +property.getURI() +"   ---" +o.toString() +"  "+n+"  "+reason);
			}
		}			
	}
	

	public boolean isNotDiverse() {
		boolean result = false;
		for(RDFNode o : objects){
			int n = occurrences.get(o);
			float reason = (float) n / propertyCounter;
			if( reason > 0.75 && n != 1) {
				result = true;
			}
		}			
		return result;
	}

}
