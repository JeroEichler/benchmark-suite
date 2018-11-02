package bsuite.step2.graphretrieval;

import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;

import bsuite.model.rdf.BasicQueryBuilder;
import bsuite.model.rdf.ModelBuilder;

public class Crawler {

	private String datasetEndpoint = "http://www.linkedmdb.org/sparql";
	private static int MAXSTEP = 1;
	
	public Crawler(String dataset) {
		this.datasetEndpoint = dataset;
	}
	
	public Model search(String resource) {
		
		Model model = search(resource, 0);
        
        return model;

	}
	
	private Model search(String resource, int step) {
		
		Model modelA = searchForward(resource, step);
		Model modelB = searchBackward(resource, step);
		
		if(modelA == null) {
			return modelB;
		}
		else if(modelB == null) {
			return modelA;
		}
		else {
			Model model = modelA.union(modelB);        
	        return model;
		}
	}
	
	
	
	private Model searchForward(String resource, int step) {
		
		Model model = ModelFactory.createDefaultModel();
		step++;
		
		Query query = BasicQueryBuilder.buildQuery(resource, false);
        try ( QueryExecution qexec = QueryExecutionFactory.sparqlService(datasetEndpoint, query) ) {
            ((QueryEngineHTTP)qexec).addParam("timeout", "10000") ;

    		// create the query result
            ResultSet results = qexec.execSelect();
            model = ModelBuilder.buildModel(resource, results);
            if(step < MAXSTEP) {
            	List<RDFNode> objs = model.listObjects().toList();
            	Model childModel = ModelFactory.createDefaultModel(); 
            	for(RDFNode obj : objs){
            		if(obj.isResource()) {
            			Resource res = (Resource) obj;
            			String uri = res.getURI();
            			Model m = search(uri, step);
            			childModel = childModel.union(m);
            		}
            		
            	}
            	
            	model = model.union(childModel);             	
            }
        } 
        catch (Exception e) {
        	Resource source = model.createResource("Query Problem");
    		Property prop = model.createProperty("occured");
    		source.addProperty(prop, "with resource".concat(resource));
//            e.printStackTrace();
            System.out.println(e);
        }
        
        return model;

	}
	
	private Model searchBackward(String resource, int step) {
		
		Model model = ModelFactory.createDefaultModel();
		step++;
		
		Query query = BasicQueryBuilder.buildQuery(resource, true);
        try ( QueryExecution qexec = QueryExecutionFactory.sparqlService(datasetEndpoint, query) ) {
            ((QueryEngineHTTP)qexec).addParam("timeout", "10000") ;

    		// create the query result
            ResultSet results = qexec.execSelect();
            model = ModelBuilder.buildInverseModel(resource, results);
            if(step < MAXSTEP) {
            	List<Resource> subjs = model.listSubjects().toList();
            	Model childModel = ModelFactory.createDefaultModel(); 
            	for(RDFNode subj : subjs){
            		if(subj.isResource()) {
            			Resource res = (Resource) subj;
            			Model m = search(res.getURI(), step);
            			childModel = childModel.union(m);
            		}            		
            	}
            	
            	model = model.union(childModel);             	
            }
        } 
        catch (Exception e) {
        	Resource source = model.createResource("Query Problem");
    		Property prop = model.createProperty("occured");
    		source.addProperty(prop, "with resource".concat(resource));
//          e.printStackTrace();
          System.out.println(e);
        }        
        return model;
	}
}
