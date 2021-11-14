package com.sampleapp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
	
	public static boolean validatePattern(String input, String regex){
		
		Matcher matcher = null;
		
		Pattern pattern  = Pattern.compile(regex);
		
		matcher = pattern.matcher(input);
		
		
		return matcher.matches();
	}
}
