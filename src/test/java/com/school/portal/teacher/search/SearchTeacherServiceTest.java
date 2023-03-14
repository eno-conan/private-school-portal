package com.school.portal.teacher.search;

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
import com.school.portal.entity.master.Classroom;
import com.school.portal.entity.master.Prefecture;
import com.school.portal.entity.master.Teacher;
import com.school.portal.repository.master.TeacherRepository;

@SpringBootTest
class SearchTeacherServiceTest {

  @Mock
  TeacherRepository repository;

  // モックを設定するテスト対象クラス
  @InjectMocks
  SearchTeacherService service;

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
  @DisplayName("講師情報取得_正常系_全件取得")
  void testSearchTeacher_success() {
    List<Teacher> teachers = createTeacher();
    // 実行時の期待結果
    when(repository.findAll()).thenReturn((teachers));
    // 実行
    service.searchTeacher(1, "Mike");

    // 検証
    assertThat(teachers.size()).isEqualTo(1);
    assertThat(teachers.get(0).getTeacherName()).isEqualTo("Mike");
  }

  /**
   * Student作成用Helperメソッド
   * 
   * @return Student
   *
   */
  private List<Teacher> createTeacher() {
    List<Teacher> teachers = new ArrayList<>();
    Teacher teacher = new Teacher();
    teacher.setId(1);
    teacher.setTeacherName("Mike");
    teacher.setBirthday(new Date());
    Prefecture mPrefecture = new Prefecture();
    mPrefecture.setId(1);
    teacher.setClassroom(new Classroom(mPrefecture, "ClassName", "Address", false,
        new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())));
    teachers.add(teacher);
    return teachers;
  }

}
