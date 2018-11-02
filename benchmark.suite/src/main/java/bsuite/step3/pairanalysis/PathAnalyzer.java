package bsuite.step3.pairanalysis;

import java.util.List;

import bsuite.model.rdf.JSONStatement;

public class PathAnalyzer {
	
	public static double analyzePath(List<List<JSONStatement>> paths) {
		double sum = 0;
		for(List<JSONStatement> path : paths) {
			if(path.size() == 1) {
				sum = sum + 0.5;
			}
			else if(path.size() == 2) {
				sum = sum + 0.25;
			}
			else if(path.size() == 3) {
				sum = sum + 0.125;
			}
		}
		return sum;
	}

}
