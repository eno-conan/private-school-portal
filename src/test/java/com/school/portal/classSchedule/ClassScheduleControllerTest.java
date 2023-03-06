package com.school.portal.classSchedule;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
class ClassScheduleControllerTest {

	@MockBean
	TargetDateClassesService service;

	private MockMvc mockMvc;

	@Autowired
	WebApplicationContext webApplicationContext;

	@BeforeEach
	void beforeEach() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.build();
	}

	@Test
	@DisplayName("授業予定取得(日付指定)：正常系")
	void testGetTargetDateClassSchedule_correct() {
		List<Map<String, Object>> classroomDummyList = createNormalClassList();
		Date date = generateDateStringtoDate();		
		when(service.getTargetDateClassSchedule(date)).thenReturn(classroomDummyList);

		try {
			//		MvcResult result = null;
			mockMvc.perform(get("/class-schedule?targetDate=2023-01-23")).andExpect(status().isOk());
			//					.andExpect(content().encoding("ISO-8859-1"))
			//					.andExpect(content().string(
			//							"[{\"classroomId\":1,\"prefectureName\":\"prefectureName\",\"classroomName\":\"classroomName\"}]"))
			//					.andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}

		verify(service, times(1)).getTargetDateClassSchedule(date);
	}
	
	@Test
	@DisplayName("授業予定取得Controller：異常系（クエリパラメータなし）")
	void testGetTargetDateClassSchedule_invalidQueryParemeter() {
		
		try {
			mockMvc.perform(get("/class-schedule")).andExpect(status().isBadRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Date date = generateDateStringtoDate();		
		verify(service, times(0)).getTargetDateClassSchedule(date);
	}

	private Date generateDateStringtoDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = null;
		try {
			date = dateFormat.parse("2023/01/23");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	private List<Map<String, Object>> createNormalClassList() {
		List<Map<String, Object>> returnJsonLiteral = new ArrayList<>();
		Map<String, Object> normalClassMap = new LinkedHashMap<>();
		normalClassMap.put("id", 1);
		normalClassMap.put("period", "6");
		normalClassMap.put("grade", "High School grade 1");
		normalClassMap.put("subject", "Math");
		normalClassMap.put("studentId", 1);
		normalClassMap.put("studentName", "Student A");
		normalClassMap.put("lecturerName", "Teacher A");
		returnJsonLiteral.add(normalClassMap);
		return returnJsonLiteral;
	}

	
}
