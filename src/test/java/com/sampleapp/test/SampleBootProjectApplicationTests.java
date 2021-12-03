package com.sampleapp.test;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


//@SpringBootTest bootstrap the full application context, so we can @Autowire 
// alternatifi @TestConfiguration, uygulamadakinden farkli test ortam tanimliyorsun, not: bunlar excluded from component scanning, @Import must be used
@SpringBootTest 
class SampleBootProjectApplicationTests {

	@Autowired
    private MockMvc mvc;
	
	@Test
	void contextLoads() {
		System.out.println("Test case 1 executing...");
	}
	
	@Test
	void restTestCase() {
		try {
			mvc.perform(get("/api/employees")
				      .contentType(MediaType.APPLICATION_JSON))
				      .andExpect(status().isOk())
//			          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON).)
				      .andExpect(jsonPath("$[0].name", is("bob")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
