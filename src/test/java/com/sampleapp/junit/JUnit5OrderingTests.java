package com.sampleapp.junit;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

// normally Junit5 creates a new instance of each test class before executing each test method , 
// to ensure individual test methods to be executed in isolation and to avoid unexpected side effects
// default is PER_METHOD
// If you are sure there is no side effect, you can run all test methods with just single test class instance (Lifecycle.PER_CLASS)
@TestInstance(Lifecycle.PER_CLASS)

//TestClassOrder exists since junit.jupiter version 5.8
//it Orders nested test classes execution along with @Order annotation above Nested classes
// @TestClassOrder(ClassOrderer.OrderAnnotation.class)

//order test methods execution
//order may be needed in separation and order of integration and functional tests
//there are other orders also likely: DisplayName, MethodName
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(TestResultLoggerExtension.class) //provides logging after test methods execution
public class JUnit5OrderingTests {
	
	  	@Test
	    @Order(1)
	    void nullValues() {
	        // perform assertions against null values
	    }

	    @Test
	    @Order(2)
	    void emptyValues() {
	        // perform assertions against empty values
	    }

	    @Test
	    @Order(3)
	    void validValues() {
	        // perform assertions against valid values
	    }
	    
	    //Nested classes help grouping test methods
	    @Nested
	    //Order for nested class exists since junit.jupiter version 5.8
	    //@Order(1)
	    class PrimaryTests {

	        @Test
	        void test1() {
	        }
	    }

	    @Nested
	    //Order for nested class exists since junit.jupiter version 5.8
	    //@Order(2)
	    class SecondaryTests {

	        @Test
	        void test2() {
	        }
	    }
}
