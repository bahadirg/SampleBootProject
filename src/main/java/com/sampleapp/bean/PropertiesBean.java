package com.sampleapp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import com.sampleapp.config.Configurations;
import com.sampleapp.model.Properties;
import com.sampleapp.repository.PropertyRepository;

import lombok.Data;

@Data 
@Component
@Scope("session")
public class PropertiesBean implements Serializable {

	@Autowired
	private PropertyRepository propertyRepository;

	private List<Properties> propertyList;
	
	private Properties property;

	public PropertiesBean() {
		propertyList = new ArrayList<Properties>();
	}

	@PostConstruct
	public void initialize() {
		propertyList = propertyRepository.findAll();
	}

	public void prepareNewProperty(ActionEvent actionEvent) {
		property = new Properties();
	}

	public void saveProperty(ActionEvent actionEvent) {
		
		if(property.getKey() == null || property.getKey().trim().equals("")) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Anahtar boş olamaz", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		
		if(property.getValue() == null || property.getValue().trim().equals("")) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Anahtar değeri boş olamaz", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		
		
		try {
			if (property.getId() == null) {
				propertyRepository.save(property);
				property = new Properties();
			} else {
				//update
				propertyRepository.save(property);
			}
			
			propertyList = propertyRepository.findAll();
			
			Configurations.refreshPropertyCacheFromDb(propertyRepository);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteProperty(ActionEvent actionEvent) {
		try {
			propertyRepository.delete(property);
			propertyList = propertyRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
