package com.school.portal.student.register;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@EnableWebMvc
class RegistStudentControllerTest {

	private MockMvc mockMvc;

	@MockBean
	RegistStudentService service;

	@Autowired
	WebApplicationContext webApplicationContext;

	@BeforeEach
	void beforeEach() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.build();
	}

	@Test
	@DisplayName("生徒登録（教室情報事前取得）：Controller")
	void testPrepareDataClassroomRegistStudent() {
		List<Map<String, Object>> classroomDummyList = createClassroomList();
		when(service.prepareClassroomData()).thenReturn(classroomDummyList);

		try {
			//		MvcResult result = null;
			mockMvc.perform(get("/student/register/prepare-classroom")).andExpect(status().isOk())
					.andExpect(content().encoding("ISO-8859-1"))
					.andExpect(content().string(
							"[{\"classroomId\":1,\"prefectureName\":\"prefectureName\",\"classroomName\":\"classroomName\"}]"))
					.andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}

		verify(service, times(1)).prepareClassroomData();
	}

	private List<Map<String, Object>> createClassroomList() {
		List<Map<String, Object>> returnJsonLiteral = new ArrayList<>();
		Map<String, Object> classroomMap = new LinkedHashMap<>();
		classroomMap.put("classroomId", 1);
		classroomMap.put("prefectureName", "prefectureName");
		classroomMap.put("classroomName", "classroomName");
		returnJsonLiteral.add(classroomMap);
		return returnJsonLiteral;
	}

}
