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
import com.school.portal.model.RegisterStudentModel;
import com.school.portal.model.api.Classroom;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@EnableWebMvc
class RegisterStudentControllerTest {

  @MockBean
  RegisterStudentService service;

  private MockMvc mockMvc;

  ObjectMapper objectMapper = new ObjectMapper();
  ObjectWriter objectWriter = objectMapper.writer();

  @Autowired
  WebApplicationContext webApplicationContext;

  @BeforeEach
  void beforeEach() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  @DisplayName("生徒登録(教室情報事前取得)_正常系_教室情報が1件以上")
  void testPrepareDataClassroomRegistStudent_success_existOverZeroClassroom() throws Exception {

    // 教室情報が1件は存在する、という前提条件で
    Classroom classroom = Classroom.builder().classroomId(1).prefectureName("prefectureName")
        .classroomName("classroomName").build();
    List<Map<String, Object>> classroomDummyList = createClassroom(classroom);
    when(service.prepareClassroomData()).thenReturn(classroomDummyList);

    // m_classroomテーブルから情報取得を実行する場合
    mockMvc.perform(get("/student/register/prepare-classroom"))
        // HTTPステータス200が取得できること
        .andExpect(status().isOk())
        // 設定したデータに関するレスポンスが取得できること
        .andExpect(content().string(
            "[{\"classroomId\":1,\"prefectureName\":\"prefectureName\",\"classroomName\":\"classroomName\"}]"))
        .andReturn();

    // 1. m_classroomテーブルの取得情報を整形するServiceクラスが1度実行されること
    // 2. Classroomオブジェクトに設定した情報が破棄されていないこと
    verify(service, times(1)).prepareClassroomData();
    assertEquals(classroom.getClassroomId(), 1);
    assertEquals(classroom.getPrefectureName(), "prefectureName");
    assertEquals(classroom.getClassroomName(), "classroomName");
  }

  private List<Map<String, Object>> createClassroom(Classroom classroom) {
    List<Map<String, Object>> returnJsonLiteral = new ArrayList<>();
    Map<String, Object> classroomMap = new LinkedHashMap<>();
    classroomMap.put("classroomId", classroom.getClassroomId());
    classroomMap.put("prefectureName", classroom.getPrefectureName());
    classroomMap.put("classroomName", classroom.getClassroomName());
    returnJsonLiteral.add(classroomMap);
    return returnJsonLiteral;
  }

  @Test
  @DisplayName("生徒登録(教室情報事前取得)_正常系_教室情報が0件")
  void testPrepareDataClassroomRegistStudent_success_existNoClassroom() throws Exception {

    // 教室情報が0件は存在する、という前提条件で
    List<Map<String, Object>> classroomDummyList = new ArrayList<>();
    when(service.prepareClassroomData()).thenReturn(classroomDummyList);

    // m_classroomテーブルから情報取得を実行する場合
    mockMvc.perform(get("/student/register/prepare-classroom"))
        // HTTPステータス200が取得できること
        .andExpect(status().isOk());

    // 期待値
    // 1. m_classroomテーブルの取得情報を整形するServiceクラスが1度実行されること
    // 2. Classroomオブジェクトに何も設定されていないこと
    verify(service, times(1)).prepareClassroomData();
    assertEquals(classroomDummyList.isEmpty(), true);
  }


  @Test
  @DisplayName("生徒登録_正常系")
  void testRegisterStudent_success() throws JsonProcessingException, Exception {

    // 生徒登録に必要な各情報が設定できているという前提条件で
    RegisterStudentModel itemRequest = new RegisterStudentModel("a", "2005-01-01", "h1", 1);

    String content = objectWriter.writeValueAsString(itemRequest);

    when(service.registerStudent(itemRequest)).thenReturn("success");

    // 生徒情報をm_studentテーブルに登録する場合
    MockHttpServletRequestBuilder mockRequest =
        MockMvcRequestBuilders.post("/student/register").contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON).content(content);

    // 期待値は、
    // 1. HTTPステータス200が取得できること
    // 2. "success"という文言が取得できていること
    // 3. 生徒登録のためのServiceクラスが実行されること
    MvcResult result = mockMvc.perform(mockRequest).andReturn();
    assertEquals(result.getResponse().getStatus(), 200);
    assertEquals(result.getResponse().getContentAsString(), "success");
    verify(service, times(1)).registerStudent(itemRequest);

  }

  @Test
  @DisplayName("生徒登録_異常系_StudentNameが空文字")
  void testRegisterStudent_failed_blankStudentName() throws JsonProcessingException, Exception {

    // 生徒登録に必要な情報のうち、「生徒名」が登録されていないという前提条件で
    RegisterStudentModel itemRequest = new RegisterStudentModel("", "2005-01-01", "h1", 1);
    String content = objectWriter.writeValueAsString(itemRequest);

    // 生徒情報をm_studentテーブルに登録する場合
    MockHttpServletRequestBuilder mockRequest =
        MockMvcRequestBuilders.post("/student/register").contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON).content(content);

    // 期待値は
    // 1. HTTPステータス400が取得できること
    // 2. Serviceクラスが一度も呼び出しされないこと
    mockMvc.perform(mockRequest).andExpect(status().isBadRequest());
    verify(service, times(0)).registerStudent(itemRequest);

  }

  @Test
  @DisplayName("生徒登録：異常系_Birthdayが空文字")
  void testRegisterStudent_failed_blankBirthday() throws JsonProcessingException, Exception {

    // 生徒登録に必要な情報のうち、「誕生日」が登録されていないという前提条件で
    RegisterStudentModel itemRequest = new RegisterStudentModel("studentName", "", "h1", 1);
    String content = objectWriter.writeValueAsString(itemRequest);

    // 生徒情報をm_studentテーブルに登録する場合
    MockHttpServletRequestBuilder mockRequest =
        MockMvcRequestBuilders.post("/student/register").contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON).content(content);

    // 期待値は
    // 1. HTTPステータス400が取得できること
    // 2. Serviceクラスが一度も呼び出しされないこと
    mockMvc.perform(mockRequest).andExpect(status().isBadRequest());
    verify(service, times(0)).registerStudent(itemRequest);

  }

  @Test
  @DisplayName("生徒登録：異常系_Grade(学年)が空文字")
  void testRegisterStudent_failed_blankGrade() throws JsonProcessingException, Exception {

    // 生徒登録に必要な情報のうち、「学年」が登録されていないという前提条件で
    RegisterStudentModel itemRequest = new RegisterStudentModel("studentName", "2005-01-01", "", 1);
    String content = objectWriter.writeValueAsString(itemRequest);

    // 生徒情報をm_studentテーブルに登録する場合
    MockHttpServletRequestBuilder mockRequest =
        MockMvcRequestBuilders.post("/student/register").contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON).content(content);

    // 期待値は
    // 1. HTTPステータス400が取得できること
    // 2. Serviceクラスが一度も呼び出しされないこと
    mockMvc.perform(mockRequest).andExpect(status().isBadRequest());
    verify(service, times(0)).registerStudent(itemRequest);

  }

  @Test
  @DisplayName("生徒登録：異常系_classroomIdが0")
  void testRegisterStudent_failed_classroomIdisZero() throws JsonProcessingException, Exception {

    // 生徒登録に必要な情報のうち、「教室ID」が不正(0)になっているという前提条件で
    RegisterStudentModel itemRequest = new RegisterStudentModel("studentName", "2005-01-01", "h1", 0);
    String content = objectWriter.writeValueAsString(itemRequest);

    // 生徒情報をm_studentテーブルに登録する場合
    MockHttpServletRequestBuilder mockRequest =
        MockMvcRequestBuilders.post("/student/register").contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON).content(content);

    // 期待値は
    // 1. HTTPステータス400が取得できること
    // 2. Serviceクラスが一度も呼び出しされないこと
    mockMvc.perform(mockRequest).andExpect(status().isBadRequest());
    verify(service, times(0)).registerStudent(itemRequest);

  }


}
