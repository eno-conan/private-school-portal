package com.school.portal.student.search;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@EnableWebMvc
class SearchStudentControllerTest {

	private MockMvc mockMvc;

	@MockBean
	SearchStudentService searchStudentService;
	
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
	void testSearchStudent() {
		when(searchStudentService.searchStudent(1,"Mike")).thenReturn("0");

		MvcResult result = null;
		try {
			result = mockMvc.perform(get("/")).andExpect(status().isOk()).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		};

		verify(searchStudentService, times(1)).searchStudent(1,"Mike");
//		assertEquals(result.getResponse(), 0);
	}

}
