package com.school.portal.student.register;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistStudentController {

	@Autowired
	private RegistStudentService registStudentService;

	//	@Autowired
	//	private PrepareService registStudentPrepareService;

	/**
	 * 生徒登録に必要なデータ取得（教室情報）
	 */
	@GetMapping("/student/register/prepare-classroom")
	List<Map<String, Object>> prepareDataClassroomRegistStudent() {
		try {
			return registStudentService.prepareClassroomData();
		} catch (Exception e) {
			return null;
		}

	}
}