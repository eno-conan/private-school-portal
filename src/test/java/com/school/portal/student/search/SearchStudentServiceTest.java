package com.school.portal.student.search;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import com.school.portal.entity.master.Classroom;
import com.school.portal.entity.master.Grade;
import com.school.portal.entity.master.Prefecture;
import com.school.portal.entity.master.Student;
import com.school.portal.exception.StudentSearchException;
import com.school.portal.repository.master.StudentRepository;

/**
 * @author Administrator
 *
 */
@SpringBootTest
@ActiveProfiles("test")
class SearchStudentServiceTest {

  @Mock
  StudentRepository repository;

  // モックを設定するテスト対象クラス
  @InjectMocks
  SearchStudentService service;

  // モックの設定と解放
  private AutoCloseable closeable;

  @BeforeEach
  public void openMocks() {
    closeable = MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  public void releaseMocks() throws Exception {
    closeable.close();
  }

  @Test
  @DisplayName("生徒情報取得_正常系_全件取得")
  void testSearchStudent_FindAll() throws StudentSearchException {
    List<Student> students = createStudent();
    // 実行時の期待結果
    when(repository.findAll()).thenReturn((students));
    // 実行
    service.searchStudent(1, "");

    // 検証
    assertThat(students.size()).isEqualTo(1);
  }

  @Test
  @DisplayName("生徒情報取得_正常系_条件設定")
  void testSearchStudent_setStudentName() throws StudentSearchException {
    List<Student> students = createStudent();
    // 実行時の期待結果
    when(repository.findByClassroomAndStudentNameLike(new Classroom(1), "Mike"))
        .thenReturn((students));
    // 実行
    service.searchStudent(1, "Mike");

    // 検証
    assertThat(students.get(0).getId()).isEqualTo(1);
    assertThat(students.get(0).getStudentName()).isEqualTo("Mike");
  }

  /**
   * Student作成用Helperメソッド
   * 
   * @return Student
   *
   */
  private List<Student> createStudent() {
    List<Student> students = new ArrayList<>();
    Student student = new Student();
    student.setId(1);
    student.setStudentName("Mike");
    student.setBirthday(new Date());
    Prefecture mPrefecture = new Prefecture();
    mPrefecture.setId(1);
    student.setClassroom(new Classroom(mPrefecture, "ClassName", "Address", false,
        new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())));
    student.setGrade(new Grade("h1"));
    students.add(student);
    return students;
  }

}
