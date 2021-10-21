package com.sampleapp.util;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class LDAPLoginVerifier {

	private String host;
	private String port;

	public LDAPLoginVerifier(final String host, String port) {
		this.host = host;
		this.port = port;
	}

	public boolean verify(String domainName, String username, String password) {
		
		DirContext ctx = null;
		
		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, "LDAP://" + host + ":" + port);
			env.put("com.sun.jndi.ldap.connect.pool", "true");
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			
			String securityPrincipal = domainName != null && domainName.trim().length() > 0 ? domainName + "\\" + username : username;
			env.put(Context.SECURITY_PRINCIPAL, securityPrincipal);
			env.put(Context.SECURITY_CREDENTIALS, password);
			ctx = new InitialDirContext(env);
			
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (ctx != null)
					ctx.close();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
}
