package com.school.portal.student.search;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.school.portal.exception.StudentSearchException;

@RestController
public class SearchStudentController {

	@Autowired
	private SearchStudentService searchStudentService;

	//	http://localhost:8080/student/search?classroomId=1&studentName=Mike
	@GetMapping("/student/search")
	List<Map<String, Object>> searchStudent(@RequestParam(name = "classroomId") final int classroomId,
			@RequestParam(name = "studentName") final String studentName) {
		try {
			return searchStudentService.searchStudent(classroomId, studentName);
		} catch (StudentSearchException e) {
			e.printStackTrace();
			return null;
		}
	}

}
