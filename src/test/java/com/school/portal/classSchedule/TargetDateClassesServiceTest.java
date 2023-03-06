package com.school.portal.classSchedule;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.school.portal.entity.StudentScheduleNormal;
import com.school.portal.entity.master.Grade;
import com.school.portal.entity.master.Lecturer;
import com.school.portal.entity.master.Student;
import com.school.portal.entity.master.Subject;
import com.school.portal.entity.master.TimeTableNormal;
import com.school.portal.repository.StudentScheduleNormalRepository;

class TargetDateClassesServiceTest {

	@Mock
	StudentScheduleNormalRepository studentScheduleNormalRepository;
	

	@InjectMocks
	TargetDateClassesService service;

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
	@DisplayName("授業予定取得(日付指定)：正常系")
	void testGetTargetDateClassSchedule() {
		List<StudentScheduleNormal> classList = createNormalClassList();
		//実行時の期待結果
		Date date = new Date();
		date = generateDateStringtoDate();
		when(studentScheduleNormalRepository.findAllByClassDateOrderByTimeTableNormalAsc(date)).thenReturn((classList));
		
		//実行
		service.getTargetDateClassSchedule(date);

		// 検証
		assertThat(classList.get(0).getId()).isEqualTo(1);
	}

	private Date generateDateStringtoDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = null;
		try {
			date = dateFormat.parse("2023/01/23");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	private List<StudentScheduleNormal> createNormalClassList() {
		List<StudentScheduleNormal> normalClassMap = new ArrayList<>();
		StudentScheduleNormal studentScheduleNormal = new StudentScheduleNormal(1);

		Lecturer lecturer = new Lecturer(1);
		studentScheduleNormal.setLecturer(lecturer);
		Subject subject = new Subject("m1");
		studentScheduleNormal.setSubject(subject);
		TimeTableNormal timeTableNormal = new TimeTableNormal(1);
		timeTableNormal.setPeriod("6");
		studentScheduleNormal.setTimeTableNormal(timeTableNormal);
		Student student = new Student();
		student.setId(1);
		student.setStudentName("Mike");
		student.setBirthday(new Date());
		student.setGrade(new Grade("h1"));
		studentScheduleNormal.setStudent(student);
		normalClassMap.add(studentScheduleNormal);
		return normalClassMap;
	}

}
