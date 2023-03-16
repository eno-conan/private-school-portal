package com.school.portal.teacher.register;

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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.school.portal.entity.master.Classroom;
import com.school.portal.entity.master.Prefecture;
import com.school.portal.entity.master.Teacher;
import com.school.portal.exception.StudentSearchException;
import com.school.portal.model.RegisterTeacherModel;
import com.school.portal.repository.master.ClassroomRepository;
import com.school.portal.repository.master.TeacherRepository;
import com.school.portal.util.UseOverFunction;
import io.github.netmikey.logunit.api.LogCapturer;

@SpringBootTest
class RegisterTeacherServiceTest {

  @InjectMocks
  RegisterTeacherService service;

  @Mock
  ClassroomRepository classroomRepository;

  @Mock
  TeacherRepository teacherRepository;

  private AutoCloseable closeable;

  // ログのキャプチャ設定
  // https://olafnosuke.hatenablog.com/entry/2022/06/17/165422
  @RegisterExtension
  LogCapturer logs = LogCapturer.create().captureForType(RegisterTeacherService.class, Level.INFO);


  @BeforeEach
  public void openMocks() {
    closeable = MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  public void releaseMocks() throws Exception {
    closeable.close();
  }

  @Test
  @DisplayName("講師登録(教室選択肢表示のための情報取得)_正常系")
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
  @DisplayName("講師登録(教室選択肢表示のための情報取得)_異常系_教室情報0件")
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
  @DisplayName("講師登録_正常系")
  void testRegisterStudent() throws StudentSearchException, JsonProcessingException {
    // リクエスト内容
    // 講師登録に必要な各情報が設定できているという前提条件で
    RegisterTeacherModel model = new RegisterTeacherModel("teacherName", "2005-01-01",
        "012-345-678", "Japan", "sample@gmail.com", 1);
    // 期待結果設定
    Teacher teacher = createTeacher(model);
    when(teacherRepository.save(any(Teacher.class))).thenReturn((teacher));
    // 実行
    String result = service.registerTeacher(model);
    // 検証
    assertEquals(result, "teacherName");
  }

  /**
   * 講師作成用Helperメソッド
   * 
   * @return Student
   *
   */
  private Teacher createTeacher(RegisterTeacherModel model) {
    Teacher teacher = new Teacher();
    teacher.setTeacherName(model.getTeacherName());
    teacher.setBirthday(UseOverFunction.convertStrDateToDateType(model.getBirthDay()));
    teacher.setClassroom(new Classroom(model.getClassroomId()));
    teacher.setAddress(model.getAddress());
    teacher.setMailAddress(model.getMailAddress());
    teacher.setDeleteFlg(false);
    teacher.setCreatedAt(new Timestamp(System.currentTimeMillis()));
    teacher.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
    return teacher;
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
    classroom.setClassroomName("teacherName");
    Prefecture mPrefecture = new Prefecture();
    mPrefecture.setId(1);
    classroom.setMPrefecture(mPrefecture);
    classroomList.add(classroom);
    return classroomList;
  }

}
