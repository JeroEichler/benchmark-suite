package bsuite.step3.pairanalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;

import bsuite.io.BasicIO;
import bsuite.io.FoldersNFiles;

public class GraphBuilder {
	
	List<List<Statement>> output = new ArrayList<List<Statement>>();
	
	
	public GraphBuilder() {
		output = new ArrayList<List<Statement>>();
	}
	
	static int nada =0;
	int maxLength =3;
	
	/* ***************************************************************************************************************
	 * Function that initializes the path finding algorithm.
	 * 
	 * The result of the function is a List<List<Statement>>, where:
	 * **** List<Statement> represents a path from source to destination.
	 * **** List<List<Statement>> represents all paths from source to destination.
	 *****************************************************************************************************************/
	public List<List<Statement>> getAllPaths(Resource source, Resource destination, HashMap<Resource,List<Statement>> graph, int length) {
		
		output = new ArrayList<List<Statement>>();
		
		maxLength = length;
		//System.out.println("==>" + source + "==>" + destination );
		HashMap<Resource,Boolean> isVisited = new HashMap<Resource,Boolean>();
		
		for(Resource res : graph.keySet()) {
			isVisited.put(res, false);
		}
		ArrayList<Statement> localPathList = new ArrayList<Statement>();
		
		getAllPathsUtil(source, destination, graph, isVisited, localPathList);
		
		return output;
	}
	
		// A recursive function to get paths from 'source' to 'destination'.
		// isVisited[] keeps track of vertices in current path.
		// localPathList<> stores actual vertices and path_index is current
		// index in localPathList<>
	private void getAllPathsUtil(
			Resource source, 
			Resource destination, 
			HashMap<Resource,List<Statement>> graph, 
			HashMap<Resource,Boolean> isVisited,
			ArrayList<Statement> localPathList
			) {
			
			nada++;
			// Mark the current node
			isVisited.put(source, true);
			
			// Arrived at destination
			if (source.equals(destination)) {
				List<Statement> newList = new ArrayList<Statement>(localPathList);
				output.add(newList);
//				System.out.println("O: "+localPathList);
			}
			// Recur for all the vertices adjacent to current vertex
			for (Statement s : graph.get(source)) {
				
				Resource subject = s.getSubject();
				Resource object = s.getResource();
				if(subject.equals(source)) {
					if (!isVisited.get(object) && localPathList.size()< maxLength) {
						//store current node in path[]
						localPathList.add(s);
						getAllPathsUtil(object, destination, graph, isVisited, localPathList);
						//remove current node in path[]
						localPathList.remove(s);
					}
				}
				else {					
					if (!isVisited.get(subject) && localPathList.size()< maxLength) {
						//store current node in path[]
						localPathList.add(s);
						getAllPathsUtil(subject, destination, graph, isVisited, localPathList);
						//remove current node in path[]
						localPathList.remove(s);
					}
				}
				
			}
			// Mark the current node
			isVisited.put(source,false);
		}
		
		
		
		
		public static void print(List<List<Statement>> list) {
			for(List<Statement> path : list) {
				System.out.println(path);
			}
			
		}
		
		/* ***************************************************************************************************************
		 * Function that converts a model in a **graph**.
		 * 
		 * The result of the function is a HashMap<Resource,List<Statement>>, where:
		 * **** Resource represents the nodes of the graph.
		 * **** Statement represents a valid transition from the Resource (a edge).
		 * **** List<Statement> represents all the valid transitions from the Resource.
		 *****************************************************************************************************************/
		public static HashMap<Resource,List<Statement>> makeList(Model model){
			
			List<String> ignoredProperties = BasicIO.readList(FoldersNFiles.ignPropFolder, FoldersNFiles.ignPropFile);
			
			List<Statement> seedStatements = model.listStatements().toList();
			
			HashMap<Resource,List<Statement>> graph = new HashMap<Resource,List<Statement>>();
			
			for(Statement stmt : seedStatements) {
				Resource subject = stmt.getSubject();
				graph.put(subject, new ArrayList<Statement>());
				
				RDFNode object = stmt.getObject();
				if(object.isResource()) {
					Resource o = (Resource) object;					
					if(!graph.containsKey(o)) {
						graph.put(o, new ArrayList<Statement>());
					}
				}
			}
			
			for(Statement stmt : seedStatements) {
				Resource subject = stmt.getSubject();
				RDFNode object = stmt.getObject();
				Property property = stmt.getPredicate();
				if(!ignoredProperties.contains(property.getURI())) {
					if(object.isResource()) {
						Resource o = (Resource) object;
						List<Statement> x = graph.get(subject);
						x.add(stmt);
						graph.put(subject, x);
						
						List<Statement> y = graph.get(o);
						
						y.add(stmt);
						graph.put(o, y);
					}
				}					
			}
			
			return graph;
		}
}
