package bsuite.model.rdf;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.Syntax;

public class BasicQueryBuilder {
	
	public static Query buildQuery(String resource, boolean backwards) {
		
		String queryStr = "";
		if(backwards) {
			queryStr = writeSqlOut(resource);
		}
		else  {
			queryStr = writeSqlIn(resource);
		}
		
		Query query = QueryFactory.create(queryStr, Syntax.syntaxARQ) ;

		return query;
	}
	
	public static String writeSqlIn(String resource) {
		
		String queryStr = " SELECT ?property ?object where {" + 
	            "	<"+resource+"> ?property ?object ." + 
	            "} "
	            ;

		return queryStr;
	}
	

	public static String writeSqlOut(String resource) {
		
		String queryStr = "SELECT ?property ?subject where {" + 
	            " ?subject ?property <"+resource+"> ." + 
	            "} "
	            ;

		return queryStr;
	}

}
