package bsuite.step4.recommendation;

import bsuite.model.entity.Entity;
import bsuite.model.entity.EntityList;
import bsuite.model.entity.EntityRecommendation;
import bsuite.model.entity.EntityScore;
import bsuite.model.entity.Genre;
import bsuite.utils.io.EntityIO;
import bsuite.utils.io.FoldersNFiles;

public class RecommViewer {
	
	static int complete = 0;
	static int uncomplete = 0;

	public static void main(String[] args) {
		int total = 0;
		int pairs = 0;
		
		for(String genre : Genre.genres()) {
			EntityList entityList = EntityIO.readEntityList(FoldersNFiles.listFolder + "//" + genre , "__" + genre);
			int entityCounter = 0;
			for(Entity entity : entityList.entities) {
				entityCounter++;
				total++;
				
				String folder1 = FoldersNFiles.recommFolder + "//" + entity.genre;
				String fileName1 = FoldersNFiles.recommPrefix + entity.normalizedTitle;
				EntityRecommendation recomm = EntityIO.loadRecommendation(folder1, fileName1);
				
				for(EntityScore pair : recomm.suggestions) {
					pairs++;
					System.out.println("Score for pair (" +recomm.base.title +","+pair.title+ ") is " +pair.score);
				}
				if(recomm.suggestions.size()<3) {
					uncomplete++;
				}
				else {
					complete++;
				}
			}
			System.out.println("### " + genre +" has " +entityCounter);
		}
		
		System.out.println("### total has " +total +" and " +pairs);
		

		System.out.println("### full has " +complete +" and " +uncomplete);
	}

}
