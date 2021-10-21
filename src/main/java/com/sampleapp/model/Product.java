package com.sampleapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data //@EqualsAndHashCode, @ToString, @Getter, @Setter on all non-final fields, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	
	@NonNull
	private Long id;
	
	private String name;
}
