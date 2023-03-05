package com.school.portal.student.register;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.school.portal.entity.master.Classroom;
import com.school.portal.entity.master.Prefecture;
import com.school.portal.exception.StudentSearchException;
import com.school.portal.repository.master.ClassroomRepository;

class RegistStudentServiceTest {

	@Mock
	ClassroomRepository classroomRepository;
	
	private AutoCloseable closeable;
	
	@InjectMocks
	RegistStudentService service;
	
	@BeforeEach
	public void openMocks() {
		closeable = MockitoAnnotations.openMocks(this);
	}

	@AfterEach
	public void releaseMocks() throws Exception {
		closeable.close();
	}

	@Test
	@DisplayName("生徒登録(教室選択肢表示のための情報取得)：正常系")
	void testPrepareClassroomData() throws StudentSearchException {
		List<Classroom> classroomList= createClassroom();
		//実行時の期待結果
		when(classroomRepository.findAll()).thenReturn((classroomList));
		//実行
		service.prepareClassroomData();

		// 検証
		assertThat(classroomList.get(0).getId()).isEqualTo(1);
	}

	/**
	 * Student作成用Helperメソッド 
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
