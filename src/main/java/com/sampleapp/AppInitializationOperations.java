package com.sampleapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sampleapp.config.Configurations;
import com.sampleapp.config.StaticConfigurations;
import com.sampleapp.enumerations.Role;
import com.sampleapp.enumerations.UserState;
import com.sampleapp.model.User;
import com.sampleapp.repository.PropertyRepository;
import com.sampleapp.repository.UserRepository;

import lombok.extern.java.Log;

//Runs just after startup

@Log
@Component
public class AppInitializationOperations implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private PropertyRepository propertiesRepository;
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		log.info("ApplicationListener#onApplicationEvent()");
		Configurations.initProperties(propertiesRepository);
		Configurations.refreshPropertyCacheFromDb(propertiesRepository);
		createAdminUserIfNotExists();
		log.info("ApplicationListener#completed!");
	}
	
	private void createAdminUserIfNotExists() {
    	
    	User user = userRepository.findByUsername(StaticConfigurations.SYSTEM_ADMIN);
		
        if (user == null) {
            user = new User();
			user.setFirstName(StaticConfigurations.SYSTEM_ADMIN);
			user.setLastName(StaticConfigurations.SYSTEM_ADMIN);
			user.setUsername(StaticConfigurations.SYSTEM_ADMIN);
			user.setPassword(passwordEncoder.encode("!Test12345"));
			user.setEmail("bguldiken@innova.com.tr");
			user.setRole(Role.ROLE_ADMIN);
			user.setState(UserState.ACTIVE);
			
			userRepository.save(user);
        }
    }
}
