package com.sampleapp.junit;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.condition.DisabledForJreRange;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;


//@RunWith(SpringRunner.class) kullanma!, bu JUnit4'te kullaniliyor
// org.junit.Assert kullanma, JUnit4'te kullaniliyor

//@ExtendWith(SpringExtension.class) kullan,  JUnit5'te kullaniliyor
//org.junit.jupiter.api.Assertions kullan, JUnit5'te kullaniliyor

//JUnit5 icin pom'da "spring-boot-starter-test" 'den "org.junit.vintage" 'i exclude etmelisin, yoksa JUnit4 geliyor 

//written according to JUnit5 version

//ref: https://junit.org/junit5/docs/current/user-guide/

@DisplayName("My Test class")
//@Disabled("All Class is Disabled until bug #99 has been fixed")
@Tag("name here used for filtering tests, can be used both at method and class level")
@ExtendWith(TestResultLoggerExtension.class) //provides logging after test methods execution
@SpringBootTest
class JUnit5IntroTests {

	@BeforeAll
    static void initAll() {
		//runs once when test is starting
		//System.out.println("BeforeAll");
    }

    @BeforeEach
    void init() {
    	//runs before each test method
    	//System.out.println("BeforeEach");
    }

    @AfterEach
    void tearDown() {
    	//runs after each test method
    	//System.out.println("AfterEach");
    }

    @AfterAll
    static void tearDownAll() {
    	//runs once when test is ending
    	//System.out.println("AfterAll");
    }
	
	@Test
	@DisplayName("Custom test name containing spaces")
	public void succeedingTest1() { 
		//System.out.println("succeedingTest1");
		assertEquals( 4, 3+1 );
	}
	
	@Test
	@Tag("name here used for filtering tests, can be used both at method and class level")
	public void succeedingTest2() { 
		//no assertion containing methods also succeed
	}
	
	@Test
    void groupedAssertions() {
        // In a grouped assertion all assertions are executed, and all
        // failures will be reported together.
        assertAll("person",
            () -> assertEquals("Jane", "Jane"),
            () -> assertEquals("Doe", "Doe")
        );
        
        // Within a code block, if an assertion fails the
        // subsequent code in the same block will be skipped.
    }
	
	@Test
	@Disabled
    void failingTest() {
        fail("a failing test");
    }

	@Test
    @Disabled("Disabled until bug #99 has been fixed")
    void skippedTest() {
        // not executed
		//System.out.println("skippedTest");
    }
	
	@Test
    void abortedTest() {
		
		//ASSUME vs ASSERT
		//assume aborts if not true, counted as skip instead of failure
		//Assumption: OS Windows, gercekte ise testin kostugu server OS Linux gibi ornegin
		assumeTrue("DEV".equals(System.getenv("ENV")),
	            () -> "Aborting test: not on developer workstation");
    }
	
    @Test
    void exceptionTesting() {
    	//Assert that execution of the supplied executable throwsan exception 
        Exception exception = assertThrows(ArithmeticException.class, () -> {int result = 1/0;});
        assertEquals("/ by zero", exception.getMessage());
    }
    
    @Test
    @Disabled
    void exceptionTesting2() {
    	int i = 1/0; //exception counted as failure instead of skip/abort
    }

    @Test
    void timeoutNotExceeded() {
    	//Assert that execution of the supplied executable completes before the given timeout is exceeded
    	//Note return type of assertTimeout changes as with return type of its 2. argument, so its dynamic, i.e. if void, it will be void
    	String actualResult = assertTimeout(Duration.ofMinutes(2), () -> {
								            // Perform task that takes less than 2 minutes.
								        	TimeUnit.SECONDS.sleep(4);
								        	return "a result";
        					  });
    	
    	//this is optional, just to show a second can be done by return value of assertTimeout
    	assertEquals("a result", actualResult);
    }
    
    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)  //this can also be set at class level
    void failsIfExecutionTimeExceeds100Milliseconds() {
        // fails if execution time exceeds 100 milliseconds
    }
	
    //conditionally enabled/disabled tests
    
    @Test
    //customCondition() method is called
    @EnabledIf("customCondition")
//  @EnabledIfEnvironmentVariable(named = "COMPUTERNAME", matches = "sysname")
//  @EnabledIfSystemProperty(named = "java.vm.vendor", matches = "Oracle.*")
    //customCondition() method in another class, i.e. ExternalCondition
    //@DisabledIf("com.example.ExternalCondition#customCondition")
    void enabledIf() {
        // ...
    }
    
    boolean customCondition() {
        return true;
    }
    
    @Test
    @EnabledOnOs({ OS.WINDOWS, OS.MAC })
    @DisabledOnOs(OS.LINUX)
    @EnabledOnJre({JRE.JAVA_10, JRE.JAVA_11})
    @DisabledForJreRange(min = JRE.JAVA_14, max = JRE.JAVA_15)
    @DisabledIfSystemProperty(named = "java.vm.vendor", matches = "Oracle.*")
    //environment variable
    @DisabledIfEnvironmentVariable(named = "LC_TIME", matches = ".*UTF-8.")
    void onlyOnMacOs() {
        // ...
    }
    
    //repeats same test 10 times
    //default display name for a given repetition is generated as "repetition {currentRepetition} of {totalRepetitions}"
    //to retrieve information about the current repetition and the total number, use RepetitionInfo injected
    @RepeatedTest(10)
    void repeatedTest() {
        // ...
    }
    
    @RepeatedTest(5)
    void repeatedTestWithRepetitionInfo(RepetitionInfo repetitionInfo) {
        assertEquals(5, repetitionInfo.getTotalRepetitions());
    }
    
    @RepeatedTest(value = 1, name = "{displayName} {currentRepetition}/{totalRepetitions}")
    @DisplayName("Repeat!")
    void customDisplayName(TestInfo testInfo) {
        assertEquals("Repeat! 1/1", testInfo.getDisplayName()); //prints "{displayName} {currentRepetition}/{totalRepetitions}"
    }
    
    
    //Parameterized tests
    
    @ParameterizedTest
    @NullSource //provides a single null argument, cant be used for primitive types
    @EmptySource //provides a single empty argument, can be used only for String,List,Set,Map,primitive arrays
    //@NullAndEmptySource   //combination of both
    //"ValueSource" accepts short,byte,int,long,float,double,char,boolean,String,Class
    @ValueSource(strings = { "racecar", "radar", "able was I ere I saw elba" }) 
    void palindromes(String candidate) {
        assertTrue("test".equals(candidate) == false);
    }
    
    @ParameterizedTest
    @EnumSource(ChronoUnit.class)
    void testWithEnumSource(TemporalUnit unit) {
        assertNotNull(unit);
    }
    
    //testing just some values of enum
    @ParameterizedTest
    @EnumSource(names = { "DAYS", "HOURS" })
    //@EnumSource(mode = EXCLUDE, names = { "ERAS", "FOREVER" }) //excluding some values for tests
    //@EnumSource(mode = MATCH_ALL, names = "^.*DAYS$")
    void testWithEnumSourceInclude(ChronoUnit unit) {
        assertTrue(EnumSet.of(ChronoUnit.DAYS, ChronoUnit.HOURS).contains(unit));
    }
    
    @ParameterizedTest
    @MethodSource("stringProvider") //if name not provided, looks for same name method, "Stream<String> testWithExplicitLocalMethodSource()" 
    void testWithExplicitLocalMethodSource(String argument) {
        assertNotNull(argument);
    }

    static Stream<String> stringProvider() {
        return Stream.of("apple", "banana");
    }
    
    //multiple parametered method tests
    @DisplayName("stringIntAndListProvider")
    @ParameterizedTest(name = "{displayName}: {index} ==> the rank of ''{0}'' is {1}")
    @MethodSource("stringIntAndListProvider")
    void testWithMultiArgMethodSource(String str, int num, List<String> list) {
        assertEquals(5, str.length());
        assertTrue(num >=1 && num <=2);
        assertEquals(2, list.size());
    }

    static Stream<Arguments> stringIntAndListProvider() {
        return Stream.of(
        		Arguments.arguments("apple", 1, Arrays.asList("a", "b")),
        		Arguments.arguments("lemon", 2, Arrays.asList("x", "y"))
        );
    }
    
    @DisplayName("testWithCsvSource")
    @ParameterizedTest(name = "{displayName}: {index} ==> the rank of ''{0}'' is {1}")
    @CsvSource({   //you can change delimiter with delimiter attribute
        "orange, 1",
        "kiwi,   2",
        "'lemon, lime', 3",
    })
    void testWithCsvSource(String fruit, int rank) {
        assertNotNull(fruit);
        assertNotEquals(0, rank);
    }
    
//    @ParameterizedTest
//    @CsvFileSource(resources = "/two-column.csv", numLinesToSkip = 1)
//    void testWithCsvFileSourceFromClasspath(String country, int reference) {
//        assertNotNull(country);
//        assertNotEquals(0, reference);
//    }
    
}
