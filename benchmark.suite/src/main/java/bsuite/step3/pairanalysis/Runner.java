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

import bsuite.io.BasicIO;
import bsuite.io.EntityIO;
import bsuite.io.ModelIO;
import bsuite.model.Entity;
import bsuite.model.EntityList;
import bsuite.utils.FoldersNFiles;

public class Runner {

	static Map<String,List<String>> dictionary;
	static int pathMaxLength = 3;

	public static void main(String[] args) {
		
		List<EntityList> domain = loadMoviesPerGenre();
		
		List<EntityList> counterDomain = loadMoviesPerGenre();
		long start = System.currentTimeMillis();
		
//		int combination = 0;
//		
//		for(EntityList list1 : domain) {
//			
//			EntityList entitiesWithPairs = new EntityList(list1.genre);
//			
//			for(Entity entityOne : list1.entities) {
//				
//				MoviePairList matchedMovies =  new MoviePairList(entityOne);
//				
//				for(EntityList list2 : counterDomain) {
//					//do not compare movies with the same genre
//					if(!dictionary.get(entityOne.normalizedTitle).contains(list2.genre)) {
//						
//						//in case movieOne appears in the list of genre (list 2) [deprecated]
//						if(!list2.getTitles().contains(entityOne.title)) {
//							for(Entity entityTwo : list2.entities) {
//								//in case movieTwo appears in two genres list, you consider it once
//								//in case movieTwo appears in genres list1
//								if((!matchedMovies.contains(entityTwo))
//										&& (NoIntersection(dictionary.get(entityOne.normalizedTitle),dictionary.get(entityTwo.normalizedTitle)))) {
////									
////											System.out.println("#O" +movieTwo.model.listStatements().toList().size());
//
//									
//									Resource res1 = ResourceFactory.createResource(entityOne.ImdbURI);
//									Resource res2 = ResourceFactory.createResource(entityTwo.ImdbURI);
//									Model model = entityOne.model.union(entityTwo.model);							
//									
//									if(model.containsResource(res1) && model.containsResource(res2)) {
//											
//										GraphBuilder p = new GraphBuilder();
//										HashMap<Resource, List<Statement>> graph = GraphBuilder.makeList(model);
//										
////										System.out.println(" P: "+movieOne.ImdbURI + " P: "+movieTwo.ImdbURI );
//										List<List<Statement>> list = p.getAllPaths(res1, res2, graph, pathMaxLength);
//										
//										if(list.size() > 0) {
//											
//											MoviePairPath pair = new MoviePairPath(entityOne, entityTwo, list);
//											MoviePairScore pairScore = new MoviePairScore(pair);
//											
//											System.out.println("#'" +list.size());
////											System.out.println("#|" +model.listStatements().toList().size());
//											
//											MoviePairLoader.savePair(pair);
//											MoviePairLoader.savePairScore(pairScore);
//											
//											matchedMovies.movies.add(entityTwo);
//											combination++;
//										}							
//										
//									}
//								}								
//							}
//						}
//						
//						
//					}					
//				}
//				
//				MoviePairLoader.savePairBaseList(matchedMovies);
//				if(matchedMovies.movies.size() > 0) {
//					entitiesWithPairs.movies.add(entityOne);
//				}
//			}
//			String folder = "pair//list//" + list1.search;
//			MovieLoader.saveMovieList(folder, list1.search, entitiesWithPairs);
//		}
//		
//		long elapsedTime = System.currentTimeMillis() - start;
//		
//		System.out.println("### Finished at "+elapsedTime/1000F+" seconds. ");
//		
//		System.out.println("### "+combination+" combination! s");

	}
	
	public static List<EntityList> loadMoviesPerGenre() {
		
		List<String> genres = BasicIO.readList(FoldersNFiles.inputFolder, FoldersNFiles.genres);
		
		dictionary = new ConcurrentHashMap<String,List<String>>();
		
		List<EntityList> movies = new ArrayList<EntityList>();

		for(String genre : genres) {
			EntityList list = EntityIO.readEntityList(FoldersNFiles.graphFolder + "//" + genre , genre); 
			for(Entity movie : list.entities) {
				Model model = ModelIO.loadModel(genre, movie.title);
				movie.model = model;
				movie.genre = genre;
				
				if(dictionary.containsKey(movie.normalizedTitle)) {
					List<String> g = dictionary.get(movie.normalizedTitle);
					if(!g.contains(genre)) {
						g.add(genre);
						dictionary.put(movie.normalizedTitle, g);
					}
				}
				else {
					List<String> g = new ArrayList<String>();
					g.add(genre);
					dictionary.put(movie.normalizedTitle, g);
				}
				
			}			
			movies.add(list);
		}
		
		return movies;		
	}
	

	public static boolean NoIntersection(List<String> lista, List<String> listb) {
		Set<String> inter = Sets.intersection(Sets.newHashSet(lista),Sets.newHashSet(listb));
		if(inter.isEmpty()) {
			return true;
		}
		
		return false;
	}
}
