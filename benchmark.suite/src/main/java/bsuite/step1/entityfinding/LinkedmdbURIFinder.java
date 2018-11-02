package bsuite.step1.entityfinding;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.Syntax;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;

import bsuite.utils.Config;
import bsuite.utils.Constants;


public class LinkedmdbURIFinder {
	
	private static final String datasetEndpoint = Config.LinkedMDB;

	
	public static String getURI(String title, int year) {
		String response = "";
		Query query = buildQuery(title, year);
        try ( QueryExecution qexec = QueryExecutionFactory.sparqlService(datasetEndpoint, query) ) {
            ((QueryEngineHTTP)qexec).addParam("timeout", "10000") ;

    		// create the query result
            ResultSet results = qexec.execSelect();
            response = extractURI(results);
            
        } 
        catch (Exception e) {
        	response = Constants.ProblemOccured;
            e.printStackTrace();
        }
        return response;
	}
	
	
	public static Query buildQuery(String title, int year) {
		
		String queryStr = writeQueryString(title, year);
		
		Query query = QueryFactory.create(queryStr, Syntax.syntaxARQ) ;

		return query;
	}
	
	public static String writeQueryString(String title, int year) {
		
		String queryStr = " not initialized."
	            ;
		
		if(year != 0) {
			queryStr = " SELECT ?subject where {" + 
		            "	?subject <http://www.w3.org/2000/01/rdf-schema#label> \"" + title +"\" . " + 
					"	?subject <http://purl.org/dc/terms/date> ?date. " + 
					"	FILTER regex(?date, \"^" +year +"\") . "+
		            "} "
		            ;
		}
		else {
			queryStr = " SELECT ?subject where {" + 
		            "	?subject <http://www.w3.org/2000/01/rdf-schema#label> \"" + title +"\" . " + 
		            "} "
		            ;
		}
		
		return queryStr;
	}
	
	public static String extractURI(ResultSet results) {

		String uriFound = Constants.NotFound;
		while(results.hasNext()){
        	QuerySolution soln = results.nextSolution() ;
        	Resource subject = soln.getResource("subject");
        	uriFound = subject.getURI();
        }
		
		return uriFound;
	}

}
