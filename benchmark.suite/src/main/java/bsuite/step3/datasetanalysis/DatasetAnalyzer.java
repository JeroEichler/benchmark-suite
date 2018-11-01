package bsuite.step3.datasetanalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Statement;

import bsuite.model.Entity;
import bsuite.model.PropertyStats;

public class DatasetAnalyzer {
	public Map<Property,PropertyStats> occurrences;
	public int total;
	
	public DatasetAnalyzer() {
		occurrences = new ConcurrentHashMap<Property,PropertyStats>();
		total = 0;
	}
	
	
	public void process(Entity entity) {
		for(Statement stmt : entity.model.listStatements().toList()) {
			Property prop = stmt.getPredicate();
			if(stmt.getObject().isResource() && stmt.getSubject().getURI().equals(entity.ImdbURI)) {
				if(occurrences.containsKey(prop)) {
					PropertyStats it = occurrences.get(prop);
					it.add(stmt.getObject());;
					occurrences.put(prop, it);
				}
				else {
					PropertyStats g = new PropertyStats(prop, stmt.getObject());
					occurrences.put(prop, g);
				}
			}
		}
		total++;
	}
	
	public void print() {
		for(PropertyStats prop : occurrences.values()) {
//			if(occurrences.get(prop) <1000 && occurrences.get(prop) >500)
//			if(occurrences.get(prop).propertyCounter >100)
//			if(occurrences.get(prop) <500 && occurrences.get(prop) >100)
			{
//				System.out.println(prop.propertyCounter + " occurences of " +prop.property.getURI() +" . "+prop.objects.size() +"| " +prop.propertyCounter/prop.objects.size());
				//prop.printValues();
				prop.printHealth();
				
			}			
		}
	}
	
	public List<String> extractUnbalancedProperties() {
		List<String> result = new ArrayList<String>();
		for(PropertyStats prop : occurrences.values()) {
			if(prop.isNotDiverse() && this.isPopular(prop))
			{
				result.add(prop.property.getURI());				
			}			
		}
		return result;
	}
	

	

	public boolean isPopular(PropertyStats prop) {
		boolean result = false;
		System.out.println(prop.property.getURI() + "  #" + prop.propertyCounter +"  "+ total);
			float reason = (float) prop.propertyCounter / total;
			if( reason > 0.5) {
				result = true;
			}
		return result;
	}
}
