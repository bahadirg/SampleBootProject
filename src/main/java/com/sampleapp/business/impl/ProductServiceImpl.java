package com.sampleapp.business.impl;

import org.springframework.stereotype.Service;

import com.sampleapp.business.ProductService;
import com.sampleapp.model.Product;

import lombok.extern.java.Log;

@Log
@Service
public class ProductServiceImpl implements ProductService {

	@Override
	public void createProduct(Product product) {
		//...
		log.info("creating product");
	}
}
