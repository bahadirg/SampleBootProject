package com.sampleapp.security;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.java.Log;

@Log
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Resource
    private CustomUserDetailService userDetailsService;
	
	@Autowired
    private CustomAuthenticationProvider customAuthProvider;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
    @Value("${authentication.type}")
    private Integer authenticationType;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		
		
		System.out.println("configure(HttpSecurity http) worked");
		
//		  .antMatchers("/admin/**").hasRole("ADMIN")
//	      .antMatchers("/anonymous*").anonymous()
//	      .antMatchers("/login*").permitAll()
//	      .anyRequest().authenticated()
		
        http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.GET, "/deneme2","/deneme3",
        		                                                              "/login","/css/**","/js/**","/fonts/**","/images/**", "/syntaxhighlighter/styles/**", 
        		                                                              "/kaptcha.jpg",
        		                                                              "/actuator/**").permitAll()
        						.antMatchers("/login.xhtml").permitAll()
        						.antMatchers("/noauthorization.xhtml").permitAll()
        						.and().formLogin().loginPage("/login.xhtml")
        						                  .usernameParameter("j_username")
        						                  .passwordParameter("j_password")
        						                  .loginProcessingUrl("/j_spring_security_check")   
        						                  .successForwardUrl("/ui/user/home.xhtml")
        						                  .defaultSuccessUrl("/ui/user/home.xhtml", true)
          						                  .failureUrl("/login.xhtml")
        						                  .permitAll().and()
        						.authorizeRequests() //.antMatchers("/ui/**").denyAll()
        						                    .antMatchers("/ui/user/**").hasAnyRole("ORDINARY", "ADMIN")
        											.antMatchers("/ui/admin/**").hasAnyRole("ADMIN")
        											.anyRequest().authenticated()
        					    .and().logout().logoutUrl("/j_spring_security_logout")
        					    			   .logoutSuccessUrl("/login.xhtml?logout")
        					    			   .invalidateHttpSession(true)
        					    			   .deleteCookies("JSESSIONID")
        					    			   .permitAll();
    }

	//https://www.javainuse.com/webseries/spring-security-jwt/chap3
	
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
    	
    	
    	if(authenticationType == null || (authenticationType < 1 ||  authenticationType > 4)) {
    		System.out.println("Invalid authenticationType property: " + authenticationType);
    	}

    	if(authenticationType == 1) {
    		
    		//Works well!
    		//username: riemann
    		//password: password
    		
    		auth.ldapAuthentication()
	            .userSearchFilter("(uid={0})")
	            .userSearchBase("dc=example,dc=com")
	            .groupSearchFilter("uniqueMember={0}")
	            .groupSearchBase("ou=mathematicians,dc=example,dc=com")
	            .userDnPatterns("uid={0}")
	            .contextSource()
	            .url("ldap://ldap.forumsys.com:389")
	            .managerDn("cn=read-only-admin,dc=example,dc=com")
	            .managerPassword("password");
    		
    	} else if(authenticationType == 2) {
    		
    		//Works well!
    		
    		auth.inMemoryAuthentication()   
	            .withUser("user").password(passwordEncoder.encode("user")).authorities("ROLE_USER").and()
	            .withUser("admin").password(passwordEncoder.encode("admin")).authorities("ROLE_USER", "ROLE_ADMIN");
    		
    	} else if(authenticationType == 3) {
    		
    		//Works well!
    		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
            authProvider.setUserDetailsService(userDetailsService);
            
            //Db'de User.password alaninda sifreli tutulan passwordu decrypt etmek icin
            authProvider.setPasswordEncoder(new BCryptPasswordEncoder()); 
    		auth.authenticationProvider(authProvider);
    		
    	} else if(authenticationType == 4) {
    		
    		auth.authenticationProvider(customAuthProvider);
    	}
    }
}
