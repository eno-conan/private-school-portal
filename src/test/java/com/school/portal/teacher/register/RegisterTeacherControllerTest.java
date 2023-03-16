package com.school.portal.teacher.register;

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
import com.school.portal.model.RegisterTeacherModel;
import com.school.portal.model.api.Classroom;



@SpringBootTest
@ExtendWith(MockitoExtension.class)
@EnableWebMvc
class RegisterTeacherControllerTest {

  @MockBean
  RegisterTeacherService service;

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
  @DisplayName("教師登録(教室情報事前取得)_正常系_教室情報が1件以上")
  void testPrepareDataClassroomRegisterTeacher_success_existOverZeroClassroom() throws Exception {
    // 教室情報が1件は存在する、という前提条件で
    Classroom classroom = Classroom.builder().classroomId(1).prefectureName("prefectureName")
        .classroomName("classroomName").build();
    List<Map<String, Object>> classroomDummyList = createClassroom(classroom);
    when(service.prepareClassroomData()).thenReturn(classroomDummyList);

    // m_classroomテーブルから情報取得を実行する場合
    mockMvc.perform(get("/teacher/register/prepare-classroom"))
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

  @Test
  @DisplayName("生徒登録(教室情報事前取得)_正常系_教室情報が0件")
  void testPrepareDataClassroomRegistStudent_success_existNoClassroom() throws Exception {

    // 教室情報が0件は存在する、という前提条件で
    List<Map<String, Object>> classroomDummyList = new ArrayList<>();
    when(service.prepareClassroomData()).thenReturn(classroomDummyList);

    // m_classroomテーブルから情報取得を実行する場合
    mockMvc.perform(get("/teacher/register/prepare-classroom"))
        // HTTPステータス200が取得できること
        .andExpect(status().isOk());

    // 期待値
    // 1. m_classroomテーブルの取得情報を整形するServiceクラスが1度実行されること
    // 2. Classroomオブジェクトに何も設定されていないこと
    verify(service, times(1)).prepareClassroomData();
    assertEquals(classroomDummyList.isEmpty(), true);
  }

  @Test
  @DisplayName("講師登録_正常系")
  void testRegisterTeacher_success() throws Exception {
    // 講師登録に必要な各情報が設定できているという前提条件で
    RegisterTeacherModel teacher =
        new RegisterTeacherModel("a", "2005-01-01", "012-345-678", "Japan", "sample@gmail.com", 1);

    String content = objectWriter.writeValueAsString(teacher);

    when(service.registerTeacher(teacher)).thenReturn("success");

    // 講師情報をm_teacherテーブルに登録する場合
    MockHttpServletRequestBuilder mockRequest =
        MockMvcRequestBuilders.post("/teacher/register").contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON).content(content);

    // 期待値は、
    // 1. HTTPステータス200が取得できること
    // 2. "success"という文言が取得できていること
    // 3. 生徒登録のためのServiceクラスが実行されること
    MvcResult result = mockMvc.perform(mockRequest).andReturn();
    assertEquals(result.getResponse().getStatus(), 200);

  }

  @Test
  @DisplayName("講師登録_異常系_teacherNameが空文字")
  void testRegisterStudent_failed_blankStudentName() throws JsonProcessingException, Exception {

    // 講師登録に必要な情報のうち、「講師名」が登録されていないという前提条件で
    RegisterTeacherModel teacher =
        new RegisterTeacherModel("", "2005-01-01", "012-345-678", "Japan", "sample@gmail.com", 1);

    String content = objectWriter.writeValueAsString(teacher);

    // 講師情報をm_teacherテーブルに登録する場合
    MockHttpServletRequestBuilder mockRequest =
        MockMvcRequestBuilders.post("/teacher/register").contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON).content(content);

    // 期待値は
    // 1. HTTPステータス400が取得できること
    // 2. Serviceクラスが一度も呼び出しされないこと
    mockMvc.perform(mockRequest).andExpect(status().isBadRequest());
    verify(service, times(0)).registerTeacher(teacher);
  }

  @Test
  @DisplayName("講師登録_異常系_birthdayが空文字")
  void testRegisterStudent_failed_blankBirthday() throws JsonProcessingException, Exception {

    // 講師登録に必要な情報のうち、「誕生日」が登録されていないという前提条件で
    RegisterTeacherModel teacher =
        new RegisterTeacherModel("teacherName", "", "012-345-678", "Japan", "sample@gmail.com", 1);

    String content = objectWriter.writeValueAsString(teacher);

    // 講師情報をm_teacherテーブルに登録する場合
    MockHttpServletRequestBuilder mockRequest =
        MockMvcRequestBuilders.post("/teacher/register").contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON).content(content);

    // 期待値は
    // 1. HTTPステータス400が取得できること
    // 2. Serviceクラスが一度も呼び出しされないこと
    mockMvc.perform(mockRequest).andExpect(status().isBadRequest());
    verify(service, times(0)).registerTeacher(teacher);
  }

  @Test
  @DisplayName("講師登録_異常系_tellNumberが空文字")
  void testRegisterStudent_failed_blankTellNumber() throws JsonProcessingException, Exception {

    // 講師登録に必要な情報のうち、「電話番号」が登録されていないという前提条件で
    RegisterTeacherModel teacher =
        new RegisterTeacherModel("teacherName", "2005-01-01", "", "Japan", "sample@gmail.com", 1);

    String content = objectWriter.writeValueAsString(teacher);

    // 講師情報をm_teacherテーブルに登録する場合
    MockHttpServletRequestBuilder mockRequest =
        MockMvcRequestBuilders.post("/teacher/register").contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON).content(content);

    // 期待値は
    // 1. HTTPステータス400が取得できること
    // 2. Serviceクラスが一度も呼び出しされないこと
    mockMvc.perform(mockRequest).andExpect(status().isBadRequest());
    verify(service, times(0)).registerTeacher(teacher);
  }

  @Test
  @DisplayName("講師登録_正常系_tellNumberが最大文字数_16文字")
  void testRegisterStudent_success_maxTellNumber_16() throws JsonProcessingException, Exception {

    // 講師登録に必要な情報のうち、「電話番号」で許容される最大文字数16文字
    RegisterTeacherModel teacher = new RegisterTeacherModel("teacherName", "2005-01-01",
        "012-345-678-1022", "Japan", "sample@gmail.com", 1);

    String content = objectWriter.writeValueAsString(teacher);

    // 講師情報をm_teacherテーブルに登録する場合
    MockHttpServletRequestBuilder mockRequest =
        MockMvcRequestBuilders.post("/teacher/register").contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON).content(content);

    // 期待値は、
    // 1. HTTPステータス200が取得できること
    // 2. "success"という文言が取得できていること
    // 3. 生徒登録のためのServiceクラスが実行されること
    MvcResult result = mockMvc.perform(mockRequest).andReturn();
    assertEquals(result.getResponse().getStatus(), 200);
  }

  @Test
  @DisplayName("講師登録_異常系_tellNumberが最大文字数超過_17文字")
  void testRegisterStudent_failed_overTellNumber_17() throws JsonProcessingException, Exception {

    // 講師登録に必要な情報のうち、「電話番号」で許容される最大文字数16文字を超過、17文字で設定
    RegisterTeacherModel teacher = new RegisterTeacherModel("teacherName", "2005-01-01",
        "012-345-678-10223", "Japan", "sample@gmail.com", 1);

    String content = objectWriter.writeValueAsString(teacher);

    // 講師情報をm_teacherテーブルに登録する場合
    MockHttpServletRequestBuilder mockRequest =
        MockMvcRequestBuilders.post("/teacher/register").contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON).content(content);

    // 期待値は
    // 1. HTTPステータス400が取得できること
    // 2. Serviceクラスが一度も呼び出しされないこと
    mockMvc.perform(mockRequest).andExpect(status().isBadRequest());
    verify(service, times(0)).registerTeacher(teacher);
  }

  @Test
  @DisplayName("講師登録_異常系_addressが空文字")
  void testRegisterStudent_failed_blankAddress() throws JsonProcessingException, Exception {

    // 講師登録に必要な情報のうち、「住所」が登録されていないという前提条件で
    RegisterTeacherModel teacher = new RegisterTeacherModel("teacherName", "2005-01-01",
        "012-345-678", "", "sample@gmail.com", 1);

    String content = objectWriter.writeValueAsString(teacher);

    // 講師情報をm_teacherテーブルに登録する場合
    MockHttpServletRequestBuilder mockRequest =
        MockMvcRequestBuilders.post("/teacher/register").contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON).content(content);

    // 期待値は
    // 1. HTTPステータス400が取得できること
    // 2. Serviceクラスが一度も呼び出しされないこと
    mockMvc.perform(mockRequest).andExpect(status().isBadRequest());
    verify(service, times(0)).registerTeacher(teacher);
  }

  @Test
  @DisplayName("講師登録_異常系_mailAddressが空文字")
  void testRegisterStudent_failed_blankMailAddress() throws JsonProcessingException, Exception {

    // 講師登録に必要な情報のうち、「メールアドレス」が登録されていないという前提条件で
    RegisterTeacherModel teacher =
        new RegisterTeacherModel("teacherName", "2005-01-01", "012-345-678", "Japan", "", 1);

    String content = objectWriter.writeValueAsString(teacher);

    // 講師情報をm_teacherテーブルに登録する場合
    MockHttpServletRequestBuilder mockRequest =
        MockMvcRequestBuilders.post("/teacher/register").contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON).content(content);

    // 期待値は
    // 1. HTTPステータス400が取得できること
    // 2. Serviceクラスが一度も呼び出しされないこと
    mockMvc.perform(mockRequest).andExpect(status().isBadRequest());
    verify(service, times(0)).registerTeacher(teacher);
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


}
