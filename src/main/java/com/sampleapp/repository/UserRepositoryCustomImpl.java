package com.sampleapp.repository;

//https://stackoverflow.com/questions/11880924/how-to-add-custom-method-to-spring-data-jpa
//https://dzone.com/articles/add-custom-functionality-to-a-spring-data-reposito

public class UserRepositoryCustomImpl implements UserRepositoryCustom {

	public void customMethod() { 
		
		System.out.println("entered customMethod");
		//... 
	}
}
