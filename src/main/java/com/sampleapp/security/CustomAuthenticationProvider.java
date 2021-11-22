package com.sampleapp.security;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sampleapp.config.StaticConfigurations;
import com.sampleapp.enumerations.UserState;
import com.sampleapp.model.User;
import com.sampleapp.repository.UserRepository;
import com.sampleapp.util.LDAPLoginVerifier;

import lombok.extern.java.Log;

@Log
@Component(value = "customAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
//	private AuthUser authUser;
	
	@Autowired
	private HttpServletRequest request;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String password = (String) authentication.getCredentials();
		String username = (String) authentication.getPrincipal();
		

		User user = userRepository.findByUsername(username);

		if (user != null) {

			if (user.getState() != UserState.ACTIVE) {
				throw new AuthenticationException("User is not active!") {};
			}

			//Kaptcha
			String kaptchaExpected = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY); 
			String kaptchaReceived = request.getParameter("j_captcha_response");
			

			if (kaptchaReceived == null || !kaptchaReceived.equalsIgnoreCase(kaptchaExpected)) { 
				throw new AuthenticationException("Captcha is wrong!") {};
			} 
	        
//			if (passwordEncoder.matches(password, user.getPassword()) == false) {
//				throw new AuthenticationException("Invalid username/password!") {};
//			}
			
			
			String host = "10.10.10.10"; 
			String baseDn = "somedomain"; //"somedomain.intra";  //ou=personel,OU=somedomain,DC=somedomain,DC=intra
//			String usern = "someuser";
//			String pass = "kZkfNfTc228GTy3";
			String port = "389";
			
			if(user.getUsername().equals(StaticConfigurations.SYSTEM_ADMIN)) {
				if (passwordEncoder.matches(password, user.getPassword()) == false) {
					throw new AuthenticationException("Invalid username/password!") {};
				}
			} else {
			
				LDAPLoginVerifier loginVerifier = new LDAPLoginVerifier(host, port);
				boolean authenticationSuccessful = loginVerifier.verify(baseDn, username, password);
				
				if(authenticationSuccessful == false){
					throw new AuthenticationException("Invalid username/password!") {};
				}
			}

			List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
	        list.add(new SimpleGrantedAuthority(user.getRole().getName()));
			return new UsernamePasswordAuthenticationToken(username, password, list);
		}
		
		throw new AuthenticationException("Bu kullanıcı adıyla sistemde tanımlı bir kullanıcı bulunamadı"){};
	}

	
	@Override
	public boolean supports(Class<? extends Object> authentication) {
		return true;
	}
}
