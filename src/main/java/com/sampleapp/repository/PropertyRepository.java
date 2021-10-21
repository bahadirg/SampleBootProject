package com.sampleapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sampleapp.model.Properties;
import com.sampleapp.model.User;

//CrudRepository
//PagingAndSortingRepository
//JpaRepository

//Multiple DB
//https://www.baeldung.com/spring-data-jpa-multiple-databases

//ExtendedRepository is custom and optional

@Repository
public interface PropertyRepository extends JpaRepository<Properties, Long>, CrudRepository<Properties, Long>, BaseRepository<Properties, Long> {
	
	Properties findByKey(String key);
	
//	User save(User entity);
//
//	Properties getOne(Long primaryKey);
//
//	List<Properties> findAll();
//
//	long count();
//
//	void delete(User entity);
//
//	boolean existsById(Long primaryKey);
}
