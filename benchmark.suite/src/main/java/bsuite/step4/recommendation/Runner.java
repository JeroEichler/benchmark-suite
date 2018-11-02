package bsuite.step4.recommendation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bsuite.model.entity.Entity;
import bsuite.model.entity.EntityList;
import bsuite.model.entity.EntityPairList;
import bsuite.model.entity.EntityPairScore;
import bsuite.model.entity.Genre;
import bsuite.utils.io.BasicIO;
import bsuite.utils.io.EntityIO;
import bsuite.utils.io.FoldersNFiles;


public class Runner {

	public static void main(String[] args) {
		int total = 0;
		int pairs = 0;
		
		for(String genre : Genre.genres()) {
			EntityList entityList = EntityIO.readEntityList(FoldersNFiles.listFolder + "//" + genre , "__" + genre);
			int movieCounter = 0;
			for(Entity entity : entityList.entities) {
				String folder = FoldersNFiles.listFolder + "//" + genre;
				String fileName = FoldersNFiles.listPrefix + entity.normalizedTitle;
				EntityPairList pairList = EntityIO.loadPairBaseList(folder, fileName);
				
				movieCounter++;
				total++;
				List<EntityPairScore> candidatePairs = new ArrayList<EntityPairScore>();
				for(Entity candidateEntity : pairList.entities) {
					String folder1 = FoldersNFiles.scoreFolder + "//" +  genre;
					String fileName1 = FoldersNFiles.scorePrefix + entity.normalizedTitle+ "-"+ candidateEntity.normalizedTitle;
					
					EntityPairScore pairScore = EntityIO.loadPairScore(folder1, fileName1);
					candidatePairs.add(pairScore);
					pairs++;
				}
				Collections.sort(candidatePairs);
				EntityRecommendation recomm = new EntityRecommendation(entity);
				recomm.addSuggestion(candidatePairs);
				
				String folder1 = FoldersNFiles.recommFolder + "//" + recomm.base.genre;
				String fileName1 = FoldersNFiles.recommPrefix + recomm.base.normalizedTitle;
				BasicIO.saveEntity(folder1, fileName1, recomm);
//				for(EntityScore pair : recomm.suggestions) {
//					System.out.println("Score for pair (" +recomm.base.title +","+pair.title+ ") is " +pair.score);
//				}
			}
			System.out.println("### " + genre +" has " +movieCounter);
		}
		
		System.out.println("### total has " +total +" and " +pairs);
	}


}
