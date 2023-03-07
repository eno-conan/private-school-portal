package com.school.portal.teacher.search;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class SearchTeacherController {

	@Autowired
	private SearchTeacherService searchTeacherService;

	//	http://localhost:8080/teacher/search?classroomId=1&teacherName=Mike
	@GetMapping("/teacher/search")
	List<Map<String, Object>> searchTeacher(@RequestParam(name = "classroomId") final int classroomId,
			@RequestParam(name = "teacherName") final String teacherName) {

		List<Map<String, Object>> teachers = searchTeacherService.searchTeacher(classroomId, teacherName);
		return teachers;
	}

}
