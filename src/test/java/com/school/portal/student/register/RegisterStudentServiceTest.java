package com.school.portal.student.register;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.event.Level;
import org.slf4j.event.LoggingEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.school.portal.entity.master.Classroom;
import com.school.portal.entity.master.Grade;
import com.school.portal.entity.master.Prefecture;
import com.school.portal.entity.master.Student;
import com.school.portal.exception.StudentSearchException;
import com.school.portal.model.RegisterStudentModel;
import com.school.portal.repository.master.ClassroomRepository;
import com.school.portal.repository.master.StudentRepository;
import com.school.portal.util.UseOverFunction;
import io.github.netmikey.logunit.api.LogCapturer;

@SpringBootTest
@ActiveProfiles("test")
class RegisterStudentServiceTest {

  @Mock
  ClassroomRepository classroomRepository;

  @Mock
  StudentRepository studentRepository;

  @InjectMocks
  RegisterStudentService service;

  private AutoCloseable closeable;

  // ログのキャプチャ設定
  // https://olafnosuke.hatenablog.com/entry/2022/06/17/165422
  @RegisterExtension
  LogCapturer logs = LogCapturer.create().captureForType(RegisterStudentService.class, Level.INFO);

  @BeforeEach
  public void openMocks() {
    closeable = MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  public void releaseMocks() throws Exception {
    closeable.close();
  }



  @Test
  @DisplayName("生徒登録(教室選択肢表示のための情報取得)_正常系")
  void testPrepareClassroomData_success() throws StudentSearchException {
    List<Classroom> classroomList = createClassroom();
    // 期待結果設定
    when(classroomRepository.findAll()).thenReturn((classroomList));
    // 実行
    service.prepareClassroomData();
    // 検証
    assertEquals(classroomList.get(0).getId(), 1);
    // ログ内容取得・検証
    List<LoggingEvent> events = logs.getEvents();
    assertEquals(events.get(0).getMessage(), "教室情報1件以上");
  }

  @Test
  @DisplayName("生徒登録(教室選択肢表示のための情報取得)_異常系_教室情報0件")
  void testPrepareClassroomData_warn() throws StudentSearchException {
    List<Classroom> classroomList = new ArrayList<>();
    // 期待結果設定
    when(classroomRepository.findAll()).thenReturn((classroomList));
    // 実行
    service.prepareClassroomData();
    // 検証
    assertEquals(classroomList.size(), 0);
    // ログ内容取得・検証
    List<LoggingEvent> events = logs.getEvents();
    assertEquals(events.get(0).getMessage(), "教室情報0件");
  }

  @Test
  @DisplayName("生徒登録_正常系")
  void testRegisterStudent() throws StudentSearchException, JsonProcessingException {
    // リクエスト内容
    RegisterStudentModel model = new RegisterStudentModel("studentName", "2005-01-01", "h1", 1);
    // 期待結果設定
    Student student = createStudent(model);
    when(studentRepository.save(any(Student.class))).thenReturn((student));
    // 実行
    String result = service.registerStudent(model);
    // 検証
    assertEquals(result, "studentName");
  }

  /**
   * 生徒作成用Helperメソッド
   * 
   * @return Student
   *
   */
  private Student createStudent(RegisterStudentModel model) {
    Student student = new Student();
    student.setStudentName(model.getStudentName());
    student.setBirthday(UseOverFunction.convertStrDateToDateType(model.getBirthDay()));
    student.setClassroom(new Classroom(model.getClassroomId()));
    student.setGrade(new Grade(model.getGrade()));
    student.setDeleteFlg(false);
    student.setCreatedAt(new Timestamp(System.currentTimeMillis()));
    student.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
    return student;
  }

  /**
   * 教室作成用Helperメソッド
   * 
   * @return Student
   *
   */
  private List<Classroom> createClassroom() {
    List<Classroom> classroomList = new ArrayList<>();
    Classroom classroom = new Classroom();
    classroom.setId(1);
    classroom.setClassroomName("studentName");
    Prefecture mPrefecture = new Prefecture();
    mPrefecture.setId(1);
    classroom.setMPrefecture(mPrefecture);
    classroomList.add(classroom);
    return classroomList;
  }

}
