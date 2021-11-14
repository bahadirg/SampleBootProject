package com.sampleapp;

import javax.faces.webapp.FacesServlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.orm.hibernate5.support.OpenSessionInViewFilter;

import com.google.code.kaptcha.servlet.KaptchaServlet;
import com.sampleapp.config.Configurations;
import com.sun.faces.config.ConfigureListener;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

import lombok.extern.java.Log;
// SpringBootServletInitializer extension of WebApplicationInitializer binds Servlet, Filter and ServletContextInitializer
// disabling default login page
// https://stackoverflow.com/questions/33655670/spring-boot-disable-security-auto-configuration 


@Log
@EnableEncryptableProperties
@SpringBootApplication(exclude = {      
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,    
		org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class
		})
@ComponentScan({ "com.sampleapp"  })
public class SampleBootProjectApplication extends SpringBootServletInitializer { 

	//works if SampleBootProjectApplication.java run from IDE or  jar from CLI 
	public static void main(String[] args) {
		SpringApplication.run(SampleBootProjectApplication.class, args);
		System.out.println("Hello World from Main");
	}
	
	//runs if packaged as war and deployed to a container
	//works before !!! Spring Boot application started
	//comes from SpringBootServletInitializer class
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    	 System.out.println("war is working!!!!!!!");	
		 return application.sources(SampleBootProjectApplication.class);
    }
    
    //kaptcha Servlet Mapping registering
    @Bean
    public ServletRegistrationBean kaptchaServletRegistration() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new KaptchaServlet(), "/kaptcha.jpg");
//        bean.setLoadOnStartup(1);
        return bean;
    }
    
    //Primefaces - JSF
    @Bean
    public ServletRegistrationBean facesServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean<>(new FacesServlet(), "*.xhtml");
        registration.setLoadOnStartup(1);
        return registration;
    }
    
    //Primefaces - JSF
    @Bean
    public ServletContextInitializer servletContextInitializer() {
        return servletContext -> {
            servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
            servletContext.setInitParameter("primefaces.THEME", "blitzer");
            //Primefaces client browser tarafında kontrol edilebilme örneğin textbox 10 karakter olmalı vs..
            servletContext.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", Boolean.TRUE.toString());
            //Xhtml sayfalarında commentlerin parse edilmemesi.
            servletContext.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", Boolean.TRUE.toString());
            //primefaces icon set için
            servletContext.setInitParameter("primefaces.FONT_AWESOME", Boolean.TRUE.toString());
            
            servletContext.setInitParameter("javax.faces.ENABLE_CDI_RESOLVER_CHAIN", Boolean.TRUE.toString());
            
            //servletContext.setInitParameter("javax.faces.DEFAULT_SUFFIX", ".xhtml");
        };
    }
    
    //Primefaces - JSF
    @Bean
    public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
        return new ServletListenerRegistrationBean<>(new ConfigureListener());
    }
}
