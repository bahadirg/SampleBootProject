package com.sampleapp.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component(value = "loggedinUserBean")
@Scope("session")
public class LoggedinUserBean implements Serializable {
	
	private Authentication authUser;
	private boolean isAdmin  = false;
	private boolean isUser   = false;
	
	
	@PostConstruct
	public void initModel() {
		authUser = SecurityContextHolder.getContext().getAuthentication();
		
		if(authUser != null && authUser.isAuthenticated()) {
			List<SimpleGrantedAuthority> grantedAuthorityList = (List<SimpleGrantedAuthority>) authUser.getAuthorities();
			
			for(SimpleGrantedAuthority authority : grantedAuthorityList) {
				if(authority.getAuthority().equals("ROLE_ADMIN")) {
					isAdmin  = true;
					isUser   = true;
				} else if(authority.getAuthority().equals("ROLE_USER")) {
					isUser   = true;
				}
			}
		}
	}

	public boolean getIsAdmin() {
		return isAdmin;
	}


	public boolean getIsUser() {
		return isUser;
	}

	public Authentication getAuthUser() {
		return authUser;
	}
}
