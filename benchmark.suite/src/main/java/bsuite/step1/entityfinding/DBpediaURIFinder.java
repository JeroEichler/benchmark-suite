package bsuite.step1.entityfinding;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;

import bsuite.model.rdf.BasicQueryBuilder;
import bsuite.model.rdf.ModelBuilder;
import bsuite.utils.Config;
import bsuite.utils.Constants;


public class DBpediaURIFinder {
	
	private static final String datasetEndpoint = Config.DBpedia;

	public static String getURI(String title, int year) {
		
		String name = title.replace(' ', '_');
		String planA = "http://dbpedia.org/resource/" + name + "_(" +year+ "_film)";
		String planB = "http://dbpedia.org/resource/" + name + "_(film)";
		String planC = "http://dbpedia.org/resource/" + name ;

		String uri1 = checkURI(planA);
		if(uri1.equals(Constants.NotFound)) {
			String uri2 = checkURI(planB);
			if(uri2.equals(Constants.NotFound)) {
				String uri3 = checkURI(planC);
				return uri3;
			}
			return uri2;			
		}
		return uri1;
	}

	private static String checkURI(String resource) {
		String response = Constants.NotFound;
		Query query = BasicQueryBuilder.buildQuery(resource, false);
        try ( QueryExecution qexec = QueryExecutionFactory.sparqlService(datasetEndpoint, query) ) {
            ((QueryEngineHTTP)qexec).addParam("timeout", "10000") ;

    		// create the query result
            ResultSet results = qexec.execSelect();
            Model model = ModelBuilder.buildModel(resource, results);
            
            if(model.isEmpty()) {
            	response = Constants.NotFound;
            }
            else {
            	String redirected = checkRedirect(model);
            	boolean NoRedirect = redirected.equals("NoRedirect");
            	if(NoRedirect) {
            		response = resource;            		
            	}
            	else {
            		response = redirected;
            	}
            }
            
        } 
        catch (Exception e) {
            e.printStackTrace();
            response = Constants.ProblemOccured;
        }
        return response;
		
	}
	
	private static String checkRedirect(Model model) {
		Property property =  ResourceFactory.createProperty("http://dbpedia.org/ontology/wikiPageRedirects");
		NodeIterator iter = model.listObjectsOfProperty(property);
		
		while (iter.hasNext()) {
		    Resource r = (Resource) iter.nextNode();
		    return r.getURI();
		}
		return "NoRedirect";
		
	}

}
