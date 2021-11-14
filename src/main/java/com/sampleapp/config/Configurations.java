package com.sampleapp.config;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sampleapp.model.Properties;
import com.sampleapp.repository.PropertyRepository;

public class Configurations {
	
	
	
	public static Integer PAGE_SIZE = 20;
	
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
	
	public static void refreshPropertyCacheFromDb(PropertyRepository propertyRepository) {
		
		List<Properties>  propList = propertyRepository.findAll();
		for(Properties p : propList) {
			try {
				Field field = Configurations.class.getDeclaredField(p.getKey());
				field.setAccessible(true);
				
				Class type = field.getType();
				
				if(type.isPrimitive()) {
					if(Integer.TYPE == type) {
						field.setInt(null, Integer.valueOf(p.getValue())); //static fieldlar icin obje vermiyoruz
					} else if(Long.TYPE == type) {
						field.setLong(null, Long.valueOf(p.getValue()));
					} else if(Short.TYPE == type) {
						field.setShort(null, Short.valueOf(p.getValue()));
					} else if(Double.TYPE == type) {
						field.setDouble(null, Double.valueOf(p.getValue()));
					} else if(Float.TYPE == type) {
						field.setFloat(null, Float.valueOf(p.getValue()));
					} else if(Byte.TYPE == type) {
						field.setByte(null, Byte.valueOf(p.getValue()));
					}
				} else {
					if(type.equals(String.class)) {
						field.set(null, p.getValue());
					} else if(type.equals(Integer.class)) {
						field.set(null, Integer.valueOf(p.getValue()));
					} else if(type.equals(Long.class)) {
						field.set(null, Long.valueOf(p.getValue()));
					} else if(type.equals(Double.class)) {
						field.set(null, Double.valueOf(p.getValue()));
					} else if(type.equals(Short.class)) {
						field.set(null, Short.valueOf(p.getValue()));
					} else if(type.equals(Byte.class)) {
						field.set(null, Byte.valueOf(p.getValue()));
					} else if(type.equals(Float.class)) {
						field.set(null, Float.valueOf(p.getValue()));
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
} 
