package com.sampleapp.util;

public class PatternBank {

	//en az 8 karakter, 
	//Büyük harf, küçük harf, nümerik veya sembol içermelidir 
	public static final String PASSWORD_STRICT_HIGHEST = "^.*(?=.{8,})(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!#$%&?]).*$";
	
	public static final String EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
}
