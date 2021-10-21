package com.sampleapp.util;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class LDAPTester {


	public static void main(String[] args) {
		
		String port = "389";
		
		//////////////////////////////////
		
		//Works
//		String host = "ldap.forumsys.com"; 
//		String domain = "uid=tesla,dc=example,dc=com";
//		String username = "tesla";
//		String password = "password";
//		Hashtable<String, String> env = new Hashtable<String, String>();
//		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
//		env.put(Context.PROVIDER_URL, "LDAP://" + host + ":" + port);
//		env.put("com.sun.jndi.ldap.connect.pool", "true");
//		env.put(Context.SECURITY_AUTHENTICATION, "simple");
//		
//		String securityPrincipal = domain; 
//		env.put(Context.SECURITY_PRINCIPAL, securityPrincipal);
//		env.put(Context.SECURITY_CREDENTIALS, password);
//		try {
//			DirContext ctx = new InitialDirContext(env);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
			
		////////////////////////////////////////
		
		String host = "10.6.96.11";  //TT
		String domain = "TURKTELEKOM";
		String username = "64519614";
		String password = "kZkfNfTc228GTy3";
		LDAPLoginVerifier ldapLoginVerifier = new LDAPLoginVerifier(host, port);   //"LDAP://10.6.96.16:389"
		boolean verificationResult = ldapLoginVerifier.verify(domain, username, password);
		
		System.out.println(" --- " + verificationResult);
	}
}
