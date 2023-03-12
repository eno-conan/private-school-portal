package com.school.portal.schedule;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.school.portal.util.UseOverFunction;

@RestController
class ClassScheduleController {

	@Autowired
	private TargetDateClassesService targetDateClassesService;

	@Autowired
	private TargetStudentClassesService targetStudentClassesService;

	/**
	 * 授業一覧取得_日付指定
	 * 
	 * @param dateStr 日付（基本的には当日の情報）
	 * @return 授業一覧
	 *
	 */
	//	http://localhost:8080/class-schedule?targetDate=2023-01-23
	@GetMapping("/class-schedule")
	List<Map<String, Object>> getTargetDateClassSchedule(@RequestParam(name = "targetDate") final String dateStr) {
		Date date = UseOverFunction.convertStrDateToDateType(dateStr);
		try {
			return targetDateClassesService.getTargetDateClassSchedule(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 授業一覧取得_生徒ID指定
	 * 
	 * @param studentId 生徒ID
	 * @return 授業一覧
	 *
	 */
	//	http://localhost:8080/class-schedule/1
	@GetMapping("/class-schedule/{studentId}")
	Map<String, Object> getTargetStudentClassSchedule(
			@PathVariable(name = "studentId", required = true) final String studentId) {
		try {
			return targetStudentClassesService.getTargeStudentClassSchedule(studentId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
