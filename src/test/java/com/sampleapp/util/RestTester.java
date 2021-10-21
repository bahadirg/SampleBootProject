package com.sampleapp.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.sampleapp.model.Product;

public class RestTester {

//	@Autowired
//	RestTemplate restTemplate;
	
	public static void main(String[] args) {
		
		RestTester tester = new RestTester();
		System.out.println(tester.isInTheList());
//		System.out.println(tester.getProductList());
	}

	public Boolean isInTheList() {
		
		  String param1 = "name999";
		  String param2 = "nameDummy";
		  Map<String, String> map = new HashMap<>();
		  map.put("name", param1);
		  map.put("name2", param2);
		  
		  
		  RestTemplate restTemplate = new RestTemplate();
		
		  //works
//		  return restTemplate.getForObject("http://localhost:9090/deneme2?name={name}&name2={name2}", 
//							   			  Boolean.class,
//							   			  param1,
//							   			  param2).booleanValue();
		  
		  //works
		  return restTemplate.getForObject("http://localhost:9090/deneme2?name={name}&name2={name2}", 
	   			  							Boolean.class,
	   			  							map).booleanValue();
		  
	}
	
	public List<Product> getProductList() {
		
		  RestTemplate restTemplate = new RestTemplate();
		
		  HttpHeaders headers = new HttpHeaders();
		  headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		  HttpEntity<String> entity = new HttpEntity<String>(headers);
		  
		  ResponseEntity<List> response = restTemplate.exchange("http://localhost:9090/deneme3", 
								                               HttpMethod.GET, 
															   entity, 
															   List.class);
		  
		  System.out.println("response StatusCode: " + response.getStatusCode());
		  return response.getBody();
									   
	}
}
