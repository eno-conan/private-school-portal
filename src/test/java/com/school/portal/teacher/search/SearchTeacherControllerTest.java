package com.school.portal.teacher.search;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@EnableWebMvc
@ActiveProfiles("test")
class SearchTeacherControllerTest {

  @MockBean
  SearchTeacherService service;

  private MockMvc mockMvc;

  @Autowired
  WebApplicationContext webApplicationContext;

  @BeforeEach
  void beforeEach() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  @DisplayName("講師取得_正常系")
  void testSearchTeacher_success() throws Exception {
    List<Map<String, Object>> teacherList = createTeacherList();
    when(service.searchTeacher(1, "teacherName")).thenReturn(teacherList);
    mockMvc.perform(get("/teacher/search?classroomId=1&teacherName=講師")).andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

    verify(service, times(1)).searchTeacher(1, "講師");
  }

  @Test
  @DisplayName("講師取得_異常系_クエリパラメータ_なし")
  void testSearchTeacher_failed() throws Exception {
    mockMvc.perform(get("/teacher/search")).andExpect(status().isBadRequest());

    verify(service, times(0)).searchTeacher(1, "講師");
  }


  @Test
  @DisplayName("講師取得_異常系_クエリパラメータ_teacherNameなし")
  void testSearchTeacher_invalidQueryParemeter_teacherName() throws Exception {
    mockMvc.perform(get("/teacher/search?classroomId=1")).andExpect(status().isBadRequest());

    verify(service, times(0)).searchTeacher(1, "講師");
  }

  @Test
  @DisplayName("講師取得_異常系_クエリパラメータ_classroomIdなし")
  void testSearchTeacher_invalidQueryParemeter() throws Exception {
    mockMvc.perform(get("/teacher/search?teacherName=講師")).andExpect(status().isBadRequest());

    verify(service, times(0)).searchTeacher(1, "講師");
  }

  private List<Map<String, Object>> createTeacherList() {
    List<Map<String, Object>> returnJsonLiteral = new ArrayList<>();
    Map<String, Object> teachersMap = new LinkedHashMap<>();
    teachersMap.put("id", 1);
    teachersMap.put("teacherName", "講師");
    returnJsonLiteral.add(teachersMap);
    return returnJsonLiteral;
  }

}
