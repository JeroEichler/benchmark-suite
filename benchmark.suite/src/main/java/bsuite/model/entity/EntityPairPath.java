package bsuite.model.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.rdf.model.Statement;

import bsuite.model.rdf.JSONStatement;

public class EntityPairPath {

	public Entity base;	
	public Entity candidate;
	public List<List<JSONStatement>> paths;
	
	public EntityPairPath(Entity baseEntity, Entity candidateEntity, List<List<Statement>> list) {
		base = baseEntity;
		candidate = candidateEntity;
		paths = convertList(list);
	}
	
	private List<List<JSONStatement>> convertList(List<List<Statement>> list){
		List<List<JSONStatement>> output = new ArrayList<List<JSONStatement>>();
		for(List<Statement> path : list) {
			List<JSONStatement> convertedPath = convertStatements(path);
			output.add(convertedPath);
		}
		return output;
	}
	
	private List<JSONStatement> convertStatements(List<Statement> list){
		List<JSONStatement> output = new ArrayList<JSONStatement>();
		for(Statement stmt : list) {
			JSONStatement convertedStmt = new JSONStatement(stmt.getSubject(), stmt.getPredicate(), stmt.getResource());
			output.add(convertedStmt);
		}
		return output;
	}
	
	
}
