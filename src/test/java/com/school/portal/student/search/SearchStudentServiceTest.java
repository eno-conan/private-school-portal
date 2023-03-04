package com.school.portal.student.search;

import static org.assertj.core.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.school.portal.entity.master.Classroom;
import com.school.portal.entity.master.Grade;
import com.school.portal.entity.master.Student;
import com.school.portal.repository.master.StudentRepository;

/**
 * @author Administrator
 *
 */
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
	void testFindAll() {
		Student student = createStudent();
		String result = service.searchStudent(1, "Mike");

		// 検証
		assertThat(student.getId()).isEqualTo(1);
		assertThat(result).isEqualTo("0");
	}

	/**
	 * Student作成用Helperメソッド 
	 * 
	 * @return Student
	 *
	 */
	private Student createStudent() {
		Student student = new Student();
		student.setId(1);
		student.setStudentName("studentName");
		student.setBirthday(new Date());
		student.setClassroom(new Classroom(1));
		student.setGrade(new Grade("h1"));
		return student;
	}

}
