package com.sampleapp.util;

import java.lang.reflect.Method;

import com.sampleapp.enumerations.UserState;

public class DummyTester {

	public static void main(String[] args) {
		
//		UserState e = UserState.ACTIVE;
//		String name = null;
//		
//		try {
//			Method method =  e.getClass().getDeclaredMethod("getName");
//			name = (String) method.invoke(e);
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//		
//		System.out.println(name);
		
		//////////////
		
		boolean b = RegexUtil.validatePattern("aaaaaaa@", PatternBank.EMAIL);
		System.out.println(b);

	}

}
