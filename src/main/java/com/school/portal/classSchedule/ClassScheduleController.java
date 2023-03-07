package com.school.portal.classSchedule;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.school.portal.util.UseOverFunction;

@RestController
class ClassScheduleController {
	
	@Autowired
	private TargetDateClassesService targetDateClassesService;
	
	/**
	 * 授業一覧取得
	 * 
	 * @param dateStr 日付（基本敵に当日の情報）
	 * @return 更新情報
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
//	[
//	  {
//		    "id": "1",
//		    "period": "6",
//		    "grade": "高校1年生",
//		    "subject": "算数",
//		    "studentId": "1",
//		    "studentName": "a",
//		    "teacherName": "講師A",
//		    "rescheduleDateStart": "2023/01/23",
//		    "rescheduleDateEnd": "2023/01/23"
//		  }
//		]


}
