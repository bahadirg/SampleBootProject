package com.sampleapp.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.primefaces.model.FilterMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

//https://www.baeldung.com/spring-data-jpa-method-in-all-repositories

//Custom BaseRepository (Optional) interface for extension by all repositories
@NoRepositoryBean
public interface BaseRepository <T, ID extends Serializable> extends JpaRepository<T, ID> {
 
	 public Long countEntities(int first, int pageSize, String sortField, String sortOrder, Map<String, FilterMeta> filters, Map<String, String> aliases, Criterion extraCriterion)
				throws SecurityException, NoSuchFieldException;
	 
	 public List<T> findEntities(int first, int pageSize, String sortField, String sortOrder, Map<String, FilterMeta> filters, Map<String, String> aliases, Criterion extraCriterion)
				throws SecurityException, NoSuchFieldException;
	 
}
