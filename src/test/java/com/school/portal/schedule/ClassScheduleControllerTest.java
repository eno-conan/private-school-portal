package com.school.portal.schedule;

import static org.junit.jupiter.api.Assertions.*;
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
import com.school.portal.model.api.NormalClass;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@EnableWebMvc
class ClassScheduleControllerTest {

  @MockBean
  TargetDateClassesService targetDateClassesService;

  @MockBean
  TargetStudentClassesService targetStudentClassesService;


  private MockMvc mockMvc;

  @Autowired
  WebApplicationContext webApplicationContext;

  @BeforeEach
  void beforeEach() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  @DisplayName("授業予定取得(日付指定)_正常系")
  void testGetTargetDateClassSchedule_success() throws Exception {
    // ある日付が設定できている、ある授業コマが準備できている、という前提条件で
    NormalClass normalClass = NormalClass.builder().classId(1).period("6").grade("h1")
        .subject("Math").studentId(1).studentName("Student A").teacherName("Teacher A").build();
    List<Map<String, Object>> classroomDummyList = createNormalClassList(normalClass);
    Date date = generateDateStringtoDate("2023/01/23");
    // Serviceクラスの戻り値を設定
    when(targetDateClassesService.getTargetDateClassSchedule(date)).thenReturn(classroomDummyList);

    // student_schedule_normalテーブルから情報取得を実行する場合
    mockMvc.perform(get("/class-schedule?targetDate=2023-01-23"))
        // HTTPステータス200が取得できること
        .andExpect(status().isOk())
        // レスポンスが設定値と一致すること
        .andExpect(
            content().string("[{\"id\":1,\"period\":\"6\",\"grade\":\"h1\",\"subject\":\"Math\","
                + "\"studentId\":1,\"studentName\":\"Student A\",\"teacherName\":\"Teacher A\"}]"));

    // 期待値
    // student_schedule_normalテーブルの取得情報を整形するServiceクラスが1度実行されること
    // NormalClass オブジェクトに何も設定されていないこと
    verify(targetDateClassesService, times(1)).getTargetDateClassSchedule(date);
    assertEquals(normalClass.getClassId(), 1);
    assertEquals(normalClass.getPeriod(), "6");
    assertEquals(normalClass.getGrade(), "h1");
    assertEquals(normalClass.getSubject(), "Math");
    assertEquals(normalClass.getStudentId(), 1);
    assertEquals(normalClass.getStudentName(), "Student A");
    assertEquals(normalClass.getTeacherName(), "Teacher A");
  }

  @Test
  @DisplayName("授業予定取得(日付指定)_異常系_クエリパラメータなし")
  void testGetTargetDateClassSchedule_invalidQueryParemeter() throws Exception {

    // ある日付が設定できていない、という前提条件で
    mockMvc.perform(get("/class-schedule"))
        // HTTPステータス400が取得できること
        .andExpect(status().isBadRequest());

    // 期待値は
    // Serviceクラスが一度も呼び出しされないこと
    Date date = generateDateStringtoDate("2023/01/23");
    verify(targetDateClassesService, times(0)).getTargetDateClassSchedule(date);
  }

  @Test
  @DisplayName("授業予定取得(生徒ID指定)_正常系_生徒ID下限値(1)")
  void testGetTargetStudentClassSchedule_success_minimamStudentId() throws Exception {
    int caseStudentId = 1;
    // ある生徒IDが設定できている、ある授業コマが準備できている、という前提条件で
    NormalClass normalClass = NormalClass.builder().classId(1).targetDate("2023/01/23").period("6")
        .grade("h1").subject("Math").studentId(caseStudentId).studentName("Student A")
        .teacherName("Teacher A").build();
    Map<String, Object> targetStudentClassList = createNormalClassListByStudent(normalClass);
    // Serviceクラスの戻り値を設定
    when(targetStudentClassesService.getTargeStudentClassSchedule(caseStudentId))
        .thenReturn(targetStudentClassList);

    // student_schedule_normalテーブルから情報取得を実行する場合
    mockMvc.perform(get("/class-schedule/student/" + caseStudentId))
        // HTTPステータス200が取得できること
        .andExpect(status().isOk())
        // レスポンスが設定値と一致すること
        .andExpect(content().string("{\"studentName\":\"Student A\",\"grade\":\"h1\","
            + "\"schedule\":[{\"id\":1,\"classDate\":\"2023/01/23\",\"period\":\"6\",\"subject\":\"Math\","
            + "\"teacherName\":\"Teacher A\"}]}"))
        .andReturn();

    // 期待値
    // student_schedule_normalテーブルの取得情報を整形するServiceクラスが1度実行されること
    // NormalClass オブジェクトに何も設定されていないこと
    verify(targetStudentClassesService, times(1)).getTargeStudentClassSchedule(caseStudentId);
    assertEquals(normalClass.getClassId(), 1);
    assertEquals(normalClass.getPeriod(), "6");
    assertEquals(normalClass.getGrade(), "h1");
    assertEquals(normalClass.getSubject(), "Math");
    assertEquals(normalClass.getStudentId(), 1);
    assertEquals(normalClass.getStudentName(), "Student A");
    assertEquals(normalClass.getTeacherName(), "Teacher A");
  }

  @Test
  @DisplayName("授業予定取得(生徒ID指定)_正常系_生徒ID上限値(10000)")
  void testGetTargetStudentClassSchedule_success_maximamStudentId() throws Exception {
    int caseStudentId = 10000;
    // ある生徒IDが設定できている、ある授業コマが準備できている、という前提条件で
    NormalClass normalClass = NormalClass.builder().classId(1).targetDate("2023/01/23").period("6")
        .grade("h1").subject("Math").studentId(caseStudentId).studentName("Student A")
        .teacherName("Teacher A").build();
    Map<String, Object> targetStudentClassList = createNormalClassListByStudent(normalClass);
    // Serviceクラスの戻り値を設定
    when(targetStudentClassesService.getTargeStudentClassSchedule(caseStudentId))
        .thenReturn(targetStudentClassList);

    // student_schedule_normalテーブルから情報取得を実行する場合
    mockMvc.perform(get("/class-schedule/student/" + caseStudentId))
        // HTTPステータス200が取得できること
        .andExpect(status().isOk())
        // レスポンスが設定値と一致すること
        .andExpect(content().string("{\"studentName\":\"Student A\",\"grade\":\"h1\","
            + "\"schedule\":[{\"id\":1,\"classDate\":\"2023/01/23\",\"period\":\"6\",\"subject\":\"Math\","
            + "\"teacherName\":\"Teacher A\"}]}"))
        .andReturn();

    // 期待値
    // student_schedule_normalテーブルの取得情報を整形するServiceクラスが1度実行されること
    // NormalClass オブジェクトに何も設定されていないこと
    verify(targetStudentClassesService, times(1)).getTargeStudentClassSchedule(caseStudentId);
    assertEquals(normalClass.getClassId(), 1);
    assertEquals(normalClass.getPeriod(), "6");
    assertEquals(normalClass.getGrade(), "h1");
    assertEquals(normalClass.getSubject(), "Math");
    assertEquals(normalClass.getStudentId(), caseStudentId);
    assertEquals(normalClass.getStudentName(), "Student A");
    assertEquals(normalClass.getTeacherName(), "Teacher A");
  }

  @Test
  @DisplayName("授業予定取得(生徒ID指定)_異常系_パスパラメータ(生徒ID)が0")
  void testGetTargetStudentClassSchedule_failed_studentIdZero() throws Exception {

    // 生徒IDが不正（0）、という前提条件で
    mockMvc.perform(get("/class-schedule/student/0"))
        // HTTPステータス400が取得できること
        .andExpect(status().isBadRequest());

    // 期待値は
    // Serviceクラスが一度も呼び出しされないこと
    verify(targetStudentClassesService, times(0)).getTargeStudentClassSchedule(1);
  }

  @Test
  @DisplayName("授業予定取得(生徒ID指定)_異常系_パスパラメータ(生徒ID)が1万より大きい")
  void testGetTargetStudentClassSchedule_failed_studentIdOverMillion() throws Exception {

    // 生徒IDが不正（0）、という前提条件で
    mockMvc.perform(get("/class-schedule/student/10001"))
        // HTTPステータス400が取得できること
        .andExpect(status().isBadRequest());

    // 期待値は
    // Serviceクラスが一度も呼び出しされないこと
    verify(targetStudentClassesService, times(0)).getTargeStudentClassSchedule(1);
  }


  private Date generateDateStringtoDate(final String targetDateStr) throws ParseException {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    Date date = null;
    date = dateFormat.parse(targetDateStr);

    return date;
  }

  private List<Map<String, Object>> createNormalClassList(final NormalClass normalClass) {
    List<Map<String, Object>> returnJsonLiteral = new ArrayList<>();
    Map<String, Object> normalClassMap = new LinkedHashMap<>();
    normalClassMap.put("id", normalClass.getClassId());
    normalClassMap.put("period", normalClass.getPeriod());
    normalClassMap.put("grade", normalClass.getGrade());
    normalClassMap.put("subject", normalClass.getSubject());
    normalClassMap.put("studentId", normalClass.getStudentId());
    normalClassMap.put("studentName", normalClass.getStudentName());
    normalClassMap.put("teacherName", normalClass.getTeacherName());
    returnJsonLiteral.add(normalClassMap);
    return returnJsonLiteral;
  }


  private Map<String, Object> createNormalClassListByStudent(final NormalClass normalClass) {
    Map<String, Object> result = new LinkedHashMap<>();
    List<Map<String, Object>> classList = new ArrayList<>();
    result.put("studentName", normalClass.getStudentName());// 生徒名
    result.put("grade", normalClass.getGrade());// 学年
    //
    Map<String, Object> infoMap = new LinkedHashMap<>();
    infoMap.put("id", normalClass.getClassId());// 授業ID
    infoMap.put("classDate", normalClass.getTargetDate());// 授業日
    infoMap.put("period", normalClass.getPeriod());// コマ
    infoMap.put("subject", normalClass.getSubject());// 授業ID
    infoMap.put("teacherName", normalClass.getTeacherName());// 授業ID
    classList.add(infoMap);
    result.put("schedule", classList);
    return result;
  }


}
