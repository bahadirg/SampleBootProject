package com.sampleapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Properties extends BaseModel {

	
	@Column(name = "KEY_EXPLANATION")
	private String keyExplanation;
	
	@Column(name = "KEY")
	private String key;

	@Column(name = "VALUE")
	private String value;

}
