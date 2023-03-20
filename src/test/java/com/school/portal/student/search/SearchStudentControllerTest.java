package com.school.portal.student.search;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import com.school.portal.exception.StudentSearchException;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@EnableWebMvc
@ActiveProfiles("test")
class SearchStudentControllerTest {

  private MockMvc mockMvc;

  @MockBean
  SearchStudentService service;

  @Autowired
  WebApplicationContext webApplicationContext;

  @BeforeEach
  void beforeEach() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext) // MockMVCをセットアップ
        .build();
  }

  @Test
  @DisplayName("生徒情報取得Controller：正常系")
  void testSearchStudent_correct() throws StudentSearchException {
    List<Map<String, Object>> returnJsonLiteral = createStudentList();
    when(service.searchStudent(1, "Mike")).thenReturn(returnJsonLiteral);

    try {
      // MvcResult result = null;
      mockMvc.perform(get("/student/search?classroomId=1&studentName=Mike"))
          .andExpect(status().isOk())
          .andExpect(content().string(
              "[{\"studentId\":1,\"studentName\":\"StudentName\",\"classroomName\":\"ClassroomName\",\"prefectureName\":\"PrefectureName\"}]"))
          .andReturn();
    } catch (Exception e) {
      e.printStackTrace();
    }

    verify(service, times(1)).searchStudent(1, "Mike");
  }

  @Test
  @DisplayName("生徒情報取得Controller：クエリパラメータなし")
  void testSearchStudent_UnSetQueryParamters() throws StudentSearchException {
    try {
      mockMvc.perform(get("/student/search")).andExpect(status().isBadRequest());
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Serviceクラス呼び出し前に処理が終了
    verify(service, times(0)).searchStudent(1, "Mike");
  }

  // @Test
  // @DisplayName("生徒情報取得Controller：例外発生")
  // void testSearchStudent_raiseException() throws StudentSearchException {
  // List<Map<String, Object>> returnJsonLiteral = createStudentList();
  // when(targetDateClassesService.searchStudent(1,
  // "Mike")).thenReturn(returnJsonLiteral).thenThrow(new StudentSearchException());
  //
  // try {
  // mockMvc.perform(get("/student/search?classroomId=1&studentName=Mike"))
  // .andExpect(status().isOk());
  // } catch (Exception e) {
  // e.printStackTrace();
  // }
  //
  // //Serviceクラス呼び出し前に処理が終了
  // verify(targetDateClassesService, times(1)).searchStudent(1, "Mike");
  // }

  private List<Map<String, Object>> createStudentList() {
    List<Map<String, Object>> returnJsonLiteral = new ArrayList<>();
    Map<String, Object> studentMap = new LinkedHashMap<>();
    studentMap.put("studentId", 1);
    studentMap.put("studentName", "StudentName");
    studentMap.put("classroomName", "ClassroomName");
    studentMap.put("prefectureName", "PrefectureName");
    returnJsonLiteral.add(studentMap);
    return returnJsonLiteral;
  }

}
