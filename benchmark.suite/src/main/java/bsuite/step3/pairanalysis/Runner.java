package bsuite.step3.pairanalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.jena.ext.com.google.common.collect.Sets;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.Statement;

import bsuite.model.entity.Entity;
import bsuite.model.entity.EntityList;
import bsuite.model.entity.EntityPairList;
import bsuite.model.entity.EntityPairPath;
import bsuite.model.entity.EntityPairScore;
import bsuite.step3.EntityLoader;
import bsuite.utils.io.BasicIO;
import bsuite.utils.io.EntityIO;
import bsuite.utils.io.FoldersNFiles;
import bsuite.utils.io.ModelIO;

public class Runner {

	static Map<String,List<String>> dictionary;
	static int pathMaxLength = 3;

	public static void main(String[] args) {
		
		List<EntityList> domain = EntityLoader.loadAllEntitiesWithModel();
		
		List<EntityList> counterDomain = EntityLoader.loadAllEntitiesWithModel();
		
		dictionary = EntityLoader.buildDictionary(domain);
		long start = System.currentTimeMillis();
		
		int combination = 0;
		
		for(EntityList list1 : domain) {
			
			EntityList entitiesWithPairs = new EntityList(list1.genre);
			
			for(Entity entityOne : list1.entities) {
				
				EntityPairList matchedEntities =  new EntityPairList(entityOne);
				
				for(EntityList list2 : counterDomain) {
					//do not compare entities with the same genre
					if(!dictionary.get(entityOne.normalizedTitle).contains(list2.genre)) {
						
						//in case entityOne appears in the list of genre (list 2) [deprecated]
						if(!list2.getTitles().contains(entityOne.title)) {
							for(Entity entityTwo : list2.entities) {
								//in case entityTwo appears in two genresFile list, you consider it once
								//in case entityTwo appears in genresFile list1
								if((!matchedEntities.contains(entityTwo))
										&& (NoIntersection(dictionary.get(entityOne.normalizedTitle),dictionary.get(entityTwo.normalizedTitle)))) {
//									
//											System.out.println("#O" +entityTwo.model.listStatements().toList().size());

									
									Resource res1 = ResourceFactory.createResource(entityOne.ImdbURI);
									Resource res2 = ResourceFactory.createResource(entityTwo.ImdbURI);
									Model model = entityOne.model.union(entityTwo.model);							
									
									if(model.containsResource(res1) && model.containsResource(res2)) {
											
										GraphBuilder p = new GraphBuilder();
										HashMap<Resource, List<Statement>> graph = GraphBuilder.makeList(model);
										
//										System.out.println(" P: "+entityOne.ImdbURI + " P: "+entityTwo.ImdbURI );
										List<List<Statement>> list = p.getAllPaths(res1, res2, graph, pathMaxLength);
										
										if(list.size() > 0) {
											
											EntityPairPath pair = new EntityPairPath(entityOne, entityTwo, list);
											EntityPairScore pairScore = new EntityPairScore(pair);
											
//											System.out.println("#'" +list.size());
//											System.out.println("#|" +model.listStatements().toList().size());
											
											String folder = FoldersNFiles.pathFolder + "//" +  pair.base.genre;
											String fileName = FoldersNFiles.pathPrefix + pair.base.normalizedTitle+ "-"+ pair.candidate.normalizedTitle;
											BasicIO.saveEntity(folder, fileName, pair);
											
											String folder1 = FoldersNFiles.scoreFolder + "//" +  pair.base.genre;
											String fileName1 = FoldersNFiles.scorePrefix + pair.base.normalizedTitle+ "-"+ pair.candidate.normalizedTitle;
											BasicIO.saveEntity(folder1, fileName1, pairScore);
											
											matchedEntities.entities.add(entityTwo);
											combination++;
										}							
										
									}
								}								
							}
						}
						
						
					}					
				}
				String folder = FoldersNFiles.listFolder + "//" + matchedEntities.base.genre;
				String fileName = FoldersNFiles.listPrefix + matchedEntities.base.normalizedTitle;
				BasicIO.saveEntity(folder, fileName, matchedEntities);
				
				if(matchedEntities.entities.size() > 0) {
					entitiesWithPairs.entities.add(entityOne);
				}
			}
			String folder = FoldersNFiles.listFolder + "//" +  list1.genre;
			System.out.println("Saved: " + folder);
			BasicIO.saveEntity(folder, "__" + list1.genre, entitiesWithPairs);
		}
		
		long elapsedTime = System.currentTimeMillis() - start;
		
		System.out.println("### Finished at "+elapsedTime/1000F+" seconds. ");
		
		System.out.println("### "+combination+" combination! s");

	}
	

	public static boolean NoIntersection(List<String> lista, List<String> listb) {
		Set<String> inter = Sets.intersection(Sets.newHashSet(lista),Sets.newHashSet(listb));
		if(inter.isEmpty()) {
			return true;
		}
		
		return false;
	}
}
