package com.toptal;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ToptalTester {
	
	public static void main(String[] args) {
		
		
//		String T[] = new String[] {"codility1", "codility3", "codility2", "codility4b", "codility4a" };
//		String R[] = new String[] {"Wrong answer", "OK", "OK", "Time limit exceeded", "OK"};
//		
//		ToptalTester t = new ToptalTester();
//		t.solution(T, R);
	}
	
	public int solution(int[] A) {
		
//		HashSet<Integer> locSet = new HashSet<Integer>();
//		HashMap<Integer,List<Integer>> locIndexMap = new HashMap<Integer,List<Integer>>();
//		for(int i=0 ; i < A.length ; i++) {
//			if(locIndexMap.get(A[i]) == null) {
//				locIndexMap.put(A[i], new ArrayList<Integer>());
//        	}
//			
//			locIndexMap.get(A[i]).add(i);
//			
//			locSet.add(A[i]);
//		}
//		
//		int minOccur = 2000000000;
//		int loc = 0;
//		
//		//find the least frequent
//		for(Integer key : locIndexMap.keySet()) {
//			if(locIndexMap.get(key).size() < minOccur) {
//				minOccur = locIndexMap.get(key).size();
//				loc = key;
//			}
//		}
//		
//		int minPath = 2000000000;
//		for() {
//			
//		}
//		
//		return 0;
		
		
		 if (A.length == 0 ||  A.length == 1) {
	            return A.length;
	        }

	        int startingIndex = 0;
	        int endingIndex = 0;
	        int[] locationVisitedCounter = new int[A.length];
	        locationVisitedCounter[A[0] - 1] = 1;

	        for (int i=1; i<A.length; i++)
	        {
	            var locationIndex = A[i] - 1;

	            locationVisitedCounter[locationIndex]++;

	            if (A[i] == A[i - 1])
	            {
	                continue;
	            }

	            endingIndex=i;

	            while (locationVisitedCounter[A[startingIndex] - 1] > 1)
	            {
	                locationVisitedCounter[A[startingIndex] - 1]--;
	                startingIndex++;
	            }

	        }

	        return endingIndex - startingIndex + 1;
    }

//	public int solution(String[] T, String[] R) {
//
//
//		HashMap<Integer,List<Boolean>> map = new HashMap<Integer,List<Boolean>>();
//		
//		for(int i=0 ; i < T.length ; i++) {
//			
//			String group = T[i].replaceAll("[^0-9]", "");
//			int groupId = Integer.valueOf(group.trim());
//        	if(map.get(groupId) == null) {
//        		map.put(groupId, new ArrayList<Boolean>());
//        	}
//        	
//        	Boolean solution = R[i].equals("OK") ? true :false; 
//        	map.get(groupId).add(solution);
//        }
//		
//		
//		Boolean isCorrect = false;
//		int count = 0;
//		int score = 0;
//		
//		//calculate result
//		for(List<Boolean> list : map.values()) {
//			
//			count++;
//			isCorrect = true;
//			
//			for(Boolean b : list) {
//				if(b.equals(false)) {
//					isCorrect = false;
//					break;
//				}
//			}
//			
//			if(isCorrect.equals(true)) {
//				score = score + 100;
//			}
//		}
//		
//		return Math.floorDiv(score,count);
//    }
}
