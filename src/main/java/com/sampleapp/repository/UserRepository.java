package com.sampleapp.repository;

import java.util.List;

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
public interface UserRepository extends JpaRepository<User, Long>, CrudRepository<User, Long>, BaseRepository<User, Long>, UserRepositoryCustom  {
	
	//method impl is auto-generated based on name, parameter
	//https://riptutorial.com/spring-data-jpa/example/20177/creating-a-repository-for-a-jpa-managed-entity
	User findByUsername(String username);
	
	////////////////////////////
	
//	User save(User entity);

//	User getOne(Long primaryKey);

//	List<User> findAll();
//
//	long count();
//
//	void delete(User entity);
//
//	boolean existsById(Long primaryKey);
}
