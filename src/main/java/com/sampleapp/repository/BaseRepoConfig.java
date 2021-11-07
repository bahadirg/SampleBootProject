package com.sampleapp.repository;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//Used for application of Custom BaseRepository (Optional) class to all repositories
// https://www.baeldung.com/spring-data-jpa-method-in-all-repositories

@Configuration
@EnableJpaRepositories(basePackages = "com.sampleapp.repository", repositoryBaseClass = BaseRepositoryImpl.class)
public class BaseRepoConfig {
	
//	@Autowired
//	private EntityManagerFactory entityManagerFactory;
	
//	@Bean
//	public Session getSession() {
//		Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
//		
//		return session;
//	}
}
