package bsuite.base.crawling;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;


public class ModelBuilder {
	
	public static Model buildModel(String resource, ResultSet results) {
		
		Model model =  ModelFactory.createDefaultModel();
		
		// create the resource
		Resource subject = model.createResource(resource);

		while(results.hasNext()){
        	QuerySolution soln = results.nextSolution() ;

        		Resource p = soln.getResource("property");
        		Property prop = ResourceFactory.createProperty(p.getURI());
        		RDFNode object = soln.get("object");
        		if(object.isResource()) {
        			Resource o = (Resource) object;
        		}
        		subject.addProperty(prop, object);
        		
			}

//		System.out.println("buildModel -------------------------------------------------");
//		Utility.printModel(model);
//		System.out.println("end --------------------------------------------------------");
		
		return model;
	}
	
	public static Model buildInverseModel(String resource, ResultSet results) {
		
		Model model =  ModelFactory.createDefaultModel();
		
		// create the resource
		Resource object = model.createResource(resource);

		while(results.hasNext()){
        	QuerySolution soln = results.nextSolution() ;

        		Resource p = soln.getResource("property");
        		Property prop = ResourceFactory.createProperty(p.getURI());
        		Resource subject = soln.getResource("subject");
//        		subject.addProperty(prop, object);
        		
        		model.add(subject, prop, object);
        		
			}

//		System.out.println("buildInverseModel ------------------------------------------");
//		Utility.printModel(model);
//		System.out.println("end --------------------------------------------------------");
		
		return model;
	}
}
