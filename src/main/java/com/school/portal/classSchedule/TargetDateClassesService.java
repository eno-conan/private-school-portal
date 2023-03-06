package com.school.portal.classSchedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.portal.entity.StudentScheduleNormal;
import com.school.portal.repository.StudentScheduleNormalRepository;

@Service
class TargetDateClassesService {

	@Autowired
	private StudentScheduleNormalRepository studentScheduleNormalRepository;

	/**
	 * // 本日の授業に関する情報を返す
	 * 
	 * @param date 情報を取得したい日付
	 * @return 授業情報
	 *
	 */
	List<Map<String, Object>> getTargetDateClassSchedule(final Date date) {
		// 本日の授業に関する情報取得
		List<StudentScheduleNormal> classList = studentScheduleNormalRepository
				.findAllByClassDateOrderByTimeTableNormalAsc(date);
		return prepareClassInfo(classList);
	}

	// 授業一覧画面表示用に整形
	private List<Map<String, Object>> prepareClassInfo(List<StudentScheduleNormal> classes) {
		List<Map<String, Object>> returnJsonLiteral = new ArrayList<>();
		for (StudentScheduleNormal cls : classes) {
			Map<String, Object> classMap = new LinkedHashMap<>();
			classMap.put("id", cls.getId());
			classMap.put("period", cls.getTimeTableNormal().getPeriod());
			classMap.put("grade", cls.getStudent().getGrade().getDisplayName());
			classMap.put("subject", cls.getSubject().getDisplayName());
			classMap.put("studentId", cls.getStudent().getId());
			classMap.put("studentName", cls.getStudent().getStudentName());
			classMap.put("lecturerName", cls.getLecturer().getLecturerName());

			//モーダルでなければ不要（画面遷移するなら、再度取得）
//			Date rescheduleDateStart = cls.getRescheduleDateStart();
//			Date rescheduleDateEnd = cls.getRescheduleDateLast();
//			classMap.put("rescheduleDateStart", rescheduleDateStart.toString().split(" ")[0]);
//			classMap.put("rescheduleDateEnd", rescheduleDateEnd.toString().split(" ")[0]);

			returnJsonLiteral.add(classMap);
		}
		return Collections.unmodifiableList(returnJsonLiteral);
	}

}
