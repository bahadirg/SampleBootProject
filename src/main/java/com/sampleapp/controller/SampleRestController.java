package com.sampleapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sampleapp.model.Product;

import lombok.extern.java.Log;

@Log
@RestController
public class SampleRestController {

	  @Value("${hosgeldin.mesaji}")
	  private String message;
	
	  @RequestMapping(value = "/deneme")
	   public String hello() {
		  log.info(message);
	      return message;
	   }
	  
	  @RequestMapping(value = "/deneme2", method = RequestMethod.GET)
	  public ResponseEntity<Object> getPrd(@RequestParam(value = "name")  String name,
			  							   @RequestParam(value = "name2")  String name2) {
		  
		  System.out.println("entered getPrd! " + name + " : " + name2);
		  
		  Boolean result = true;
		  return new ResponseEntity<>(result, HttpStatus.OK);
	  }
	  
	  //returning a list
	  @RequestMapping(value = "/deneme3", method = RequestMethod.GET)
	  public ResponseEntity<Object> getProduct() {
		  
		  List<Product> productList = new ArrayList<Product>();
		  Product prd = new Product(1L,"prd1");
		  productList.add(prd);
		  prd = new Product(2L,"prd2");
		  productList.add(prd);
		  return new ResponseEntity<>(productList, HttpStatus.OK);
	  }
	  
//	  @GetMapping("/redirect")
//	  public void getRedirect(HttpServletResponse resp, HttpServletRequest request) throws IOException {
//	   
//		   System.out.println("Auth: " + SecurityContextHolder.getContext().getAuthentication());
//	
//		   Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		   System.out.println("Principal: " + auth.getPrincipal());
//		   System.out.println("Authorities: " + auth.getAuthorities()); //get it from here OR
//		   System.out.println(request.isUserInRole("ROLE_USER")); // get it from Here as WELL (ONLY IF YOU HAVE A REQUEST)
//	
//		   resp.sendRedirect("/ui/home.xhtml");
//	  }
}
