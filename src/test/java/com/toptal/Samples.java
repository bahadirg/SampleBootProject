package com.toptal;

import java.util.Arrays;
import java.util.HashMap;

public class Samples {

	
	public int solution(int[] A) {
        // write your code in Java SE 8
        Arrays.sort(A);
        
        //--------------------
        
        HashMap<Integer,String> map = new HashMap<Integer,String>();
        //map.put(null, null)
        
        //-----------------------
        
        for(int i=0 ; i < A.length ; i++) {
        	
        }
        
      //--------------------
        
        int index = 1;
        
        for(int i=0 ; i < A.length ; i++) {
        	if(A[i] != index) {
        		if(A[i] > index) {
        			return index;
        		}
        	} else {
        		index++;
        	}
        }
        
        return index;
    }
	
}
