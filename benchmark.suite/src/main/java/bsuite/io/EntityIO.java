package bsuite.io;

import java.io.File;
import java.io.IOException;

import org.apache.jena.rdf.model.Model;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import bsuite.model.EntityList;
import bsuite.model.EntityPairList;
import bsuite.model.EntityPairScore;
import bsuite.step4.recommendation.EntityRecommendation;
import bsuite.utils.FoldersNFiles;
import bsuite.utils.MapperFactory;

public class EntityIO {	

	static ObjectMapper mapper = MapperFactory.initMapper(); 
	
	public static EntityList readEntityList(String folder, String genre) {
		EntityList entityList = new EntityList(genre);
		
		try {
			entityList = mapper.readValue(new File(
					FoldersNFiles.root +
					folder +"//" +
					genre +".json"), EntityList.class);
		} 
		catch (JsonParseException e) {
			e.printStackTrace();
		} 
		catch (JsonMappingException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return entityList;
	}
	
	public static void saveProgress(String folder, String genre, EntityList entityList, boolean sucess) {
		String title = "_crawled titles.json";
		if(!sucess) {
			title = "_not_crawled titles.json";
		}
		
		try {
			mapper.writeValue(new File(
					FoldersNFiles.root +
					folder +"//" +
					genre +"//" +
					title), entityList);
			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveGraph(String folder, String genre, String title, Model model) {
		
		String path = FoldersNFiles.root +
						folder +"//" +
						genre +"//"+ 
						title +".ttl";
			
		ModelIO.saveModel(path, model);	
	}
	
	public static Model loadGraph(String folder, String genre, String title) {
		
		String path = FoldersNFiles.root +
						folder +"//" +
						genre +"//"+ 
						title +".ttl";
			
		Model model = ModelIO.loadModel(path);	
		return model;
	}
	
	public static EntityPairScore loadPairScore(String folder, String title) {
		EntityPairScore pairScore = null;		

		try {
			pairScore = mapper.readValue(new File(
					FoldersNFiles.root +
					folder +"//" +
					title + ".json"), 
					EntityPairScore.class);
		} 
		catch (JsonParseException e) {
			e.printStackTrace();
		} 
		catch (JsonMappingException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return pairScore;
	}

	public static EntityPairList loadPairBaseList(String folder, String title) {
		EntityPairList pairList = null;		

		try {
			pairList = mapper.readValue(new File(
					FoldersNFiles.root +
					folder +"//" +
					title + ".json"), 
					EntityPairList.class);
		} 
		catch (JsonParseException e) {
			e.printStackTrace();
		} 
		catch (JsonMappingException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return pairList;
	}

	public static EntityRecommendation loadRecommendation(String folder, String fileName) {
		EntityRecommendation pairList = null;		

		try {
			pairList = mapper.readValue(new File(
					FoldersNFiles.root +
					folder +"//" +
					fileName + ".json"), 
					EntityRecommendation.class);
		} 
		catch (JsonParseException e) {
			e.printStackTrace();
		} 
		catch (JsonMappingException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return pairList;
	}		

}
