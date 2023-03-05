package com.school.portal.base;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//		MockitoAnnotations.initMocks(this);
//		https://www.arhohuttunen.com/junit-5-mockito/
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@EnableWebMvc
class BaseControllerTest {

	private MockMvc mockMvc;

	@Autowired
	WebApplicationContext webApplicationContext;

	@BeforeEach
	void beforeEach() {
		//		MockitoAnnotations.initMocks(this);
		//		https://www.arhohuttunen.com/junit-5-mockito/
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext) // MockMVCをセットアップ
				.build();
	}

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
