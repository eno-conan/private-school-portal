package com.school.portal.student.register;

import static org.junit.jupiter.api.Assertions.*;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.school.portal.model.RegistStudentModel;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@EnableWebMvc
class RegistStudentControllerTest {

	@MockBean
	RegistStudentService service;

	private MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectWriter = objectMapper.writer();

	@Autowired
	WebApplicationContext webApplicationContext;

	@BeforeEach
	void beforeEach() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.build();
	}

	@Test
	@DisplayName("生徒登録（教室情報事前取得）_正常系")
	void testPrepareDataClassroomRegistStudent() throws Exception {
		//　どういった情報を設定しているか、Helperメソッドで隠れている
		// 全部の引数を設定できるコンストラクタに値を設定する部分を見せればよい。
		// Map型が戻り値になっているメソッドを設定した方がいいかな
		List<Map<String, Object>> classroomDummyList = createClassroomList();
		when(service.prepareClassroomData()).thenReturn(classroomDummyList);

		//		MvcResult result = null;
		mockMvc.perform(get("/student/register/prepare-classroom")).andExpect(status().isOk())
				.andExpect(content().encoding("ISO-8859-1"))
				.andExpect(content().string(
						"[{\"classroomId\":1,\"prefectureName\":\"prefectureName\",\"classroomName\":\"classroomName\"}]"))
				.andReturn();

		verify(service, times(1)).prepareClassroomData();
	}

	@Test
	@DisplayName("生徒登録_正常系")
	void testRegisterStudent_success() throws JsonProcessingException, Exception {

		//どういった情報を設定しているか、Helperメソッドで隠れていないので、OK
		// 生徒登録に必要な情報全て正しく設定できているという前提条件で
		RegistStudentModel itemRequest = new RegistStudentModel("a", "2005-01-01", "h1", 1);

		String content = objectWriter.writeValueAsString(itemRequest);

		// 生徒情報をm_studentテーブルに登録する場合
		when(service.registerStudent(itemRequest)).thenReturn("success");

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/student/register")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(content);

		MvcResult result = mockMvc.perform(mockRequest).andExpect(status().isOk()).andReturn();

		// その場合は、"success"という文言が取得できていること
		assertEquals(result.getResponse().getContentAsString(), "success");
		verify(service, times(1)).registerStudent(itemRequest);

	}

	@Test
	@DisplayName("生徒登録_異常系_StudentNameが空文字")
	void testRegisterStudent_failed_blankStudentName() throws JsonProcessingException, Exception {

		// 生徒登録に必要な情報のうち、「生徒名」が登録されていないという前提条件で
		RegistStudentModel itemRequest = new RegistStudentModel("", "2005-01-01", "h1", 1);
		String content = objectWriter.writeValueAsString(itemRequest);

		// 生徒情報をm_studentテーブルに登録する場合
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/student/register")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(content);

		// その場合は、HTTPステータス400が取得できて、Serviceクラスが一度も呼び出しされないこと
		mockMvc.perform(mockRequest).andExpect(status().isBadRequest());
		verify(service, times(0)).registerStudent(itemRequest);

	}

	@Test
	@DisplayName("生徒登録：異常系_Birthdayが空文字")
	void testRegisterStudent_failed_blankBirthday() throws JsonProcessingException, Exception {

		// 生徒登録に必要な情報のうち、「誕生日」が登録されていないという前提条件で		
		RegistStudentModel itemRequest = new RegistStudentModel("studentName", "", "h1", 1);
		String content = objectWriter.writeValueAsString(itemRequest);

		// 生徒情報をm_studentテーブルに登録する場合		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/student/register")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(content);

		// その場合は、HTTPステータス400が取得できて、Serviceクラスが一度も呼び出しされないこと
		mockMvc.perform(mockRequest).andExpect(status().isBadRequest());
		verify(service, times(0)).registerStudent(itemRequest);

	}

	@Test
	@DisplayName("生徒登録：異常系_Grade(学年)が空文字")
	void testRegisterStudent_failed_blankGrade() throws JsonProcessingException, Exception {

		// 生徒登録に必要な情報のうち、「学年」が登録されていないという前提条件で
		RegistStudentModel itemRequest = new RegistStudentModel("studentName", "2005-01-01", "", 1);
		String content = objectWriter.writeValueAsString(itemRequest);

		// 生徒情報をm_studentテーブルに登録する場合		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/student/register")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(content);

		// その場合は、HTTPステータス400が取得できて、Serviceクラスが一度も呼び出しされないこと				
		mockMvc.perform(mockRequest).andExpect(status().isBadRequest());
		verify(service, times(0)).registerStudent(itemRequest);

	}

	@Test
	@DisplayName("生徒登録：異常系_classroomIdが0")
	void testRegisterStudent_failed_classroomIdisZero() throws JsonProcessingException, Exception {

		// 生徒登録に必要な情報のうち、「教室ID」が不正(0)になっているという前提条件で
		RegistStudentModel itemRequest = new RegistStudentModel("studentName", "2005-01-01", "h1", 0);
		String content = objectWriter.writeValueAsString(itemRequest);

		// 生徒情報をm_studentテーブルに登録する場合		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/student/register")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(content);

		// その場合は、HTTPステータス400が取得できて、Serviceクラスが一度も呼び出しされないこと
		mockMvc.perform(mockRequest).andExpect(status().isBadRequest());
		verify(service, times(0)).registerStudent(itemRequest);

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
