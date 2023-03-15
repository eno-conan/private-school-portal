package com.school.portal.teacher.register;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;



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
  void testPrepareDataClassroomRegisterTeacher() {
    fail("まだ実装されていません");
  }

  @Test
  void testRegisterTeacher() {
    fail("まだ実装されていません");
  }

}
