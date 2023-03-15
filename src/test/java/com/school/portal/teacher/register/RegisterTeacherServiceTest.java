package com.school.portal.teacher.register;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.event.Level;
import org.springframework.boot.test.context.SpringBootTest;
import com.school.portal.repository.master.ClassroomRepository;
import com.school.portal.repository.master.TeacherRepository;
import io.github.netmikey.logunit.api.LogCapturer;

@SpringBootTest
class RegisterTeacherServiceTest {

  @Mock
  ClassroomRepository classroomRepository;

  @InjectMocks
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
  void testRegisterTeacher() {
    fail("まだ実装されていません");
  }

  @Test
  void testPrepareClassroomData() {
    fail("まだ実装されていません");
  }

}
