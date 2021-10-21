package com.sampleapp.config;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sampleapp.model.Properties;
import com.sampleapp.repository.PropertyRepository;

public class Configurations {
	
	
	
	public static final Integer PAGE_SIZE = 20;
	
	public static void initProperties(PropertyRepository propertyRepository) {

		Field field = null;
		Properties prop = null;
		Set<String> keySet = new HashSet<String>();
		
		List<Properties>  propList = propertyRepository.findAll();
		for(Properties p : propList) {
			keySet.add(p.getKey());
		}
		
		Field[] fields = Configurations.class.getDeclaredFields();
		
		for(int i = 0; i < fields.length; i++) {
			
			field = fields[i];
			
			try {
				if(keySet.contains(field.getName()) == false) {
					prop = new Properties(null, field.getName(), field.get(Configurations.class).toString());
					propertyRepository.save(prop);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
} 
