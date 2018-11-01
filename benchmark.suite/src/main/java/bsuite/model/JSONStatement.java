package bsuite.model;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

public class JSONStatement {
	
	public JSONStatement(Resource s, Property p, Resource o) {
		this.subjectURI = s.getURI();
		this.predicateURI = p.getURI();
		this.objectURI = o.getURI();
	}
	
	public String subjectURI;
	public String predicateURI;
	public String objectURI;

}
