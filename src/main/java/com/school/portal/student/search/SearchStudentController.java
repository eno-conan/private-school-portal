package com.school.portal.student.search;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class SearchStudentController {

	@Autowired
	private SearchStudentService searchStudentService;

	@GetMapping("/student/search")
	List<Map<String, Object>> searchStudent() {
		try {
			return searchStudentService.searchStudent(1, "Mike");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		//		return searchStudentService.searchStudent(Integer.parseIntclassroomId), studentName);
		//			return "Hello World";	
	}
	//	String searchStudent(@RequestParam(name = "classroomId") final String classroomId,
	//			@RequestParam(name = "studentName") final String studentName) {
	//		try {
	//			return searchStudentService.searchStudent(classroomId, studentName);
	//		} catch (JsonProcessingException e) {
	//			return e.getMessage();
	//		} catch (RegistStudentException e) {
	//			return e.getMessage();
	//		}
	//	}

}
