package com.school.portal.base;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;

class BaseControllerTest {
	
	private MockMvc mockMvc;

	@Test
	@DisplayName("base：Controller")	
	void base() {
		try {
			mockMvc.perform(get("/")).andExpect(status().isOk()).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
