package com.school.portal.teacher.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.portal.entity.master.Classroom;
import com.school.portal.entity.master.Teacher;
import com.school.portal.repository.master.TeacherRepository;

@Service
class SearchTeacherService {

	@Autowired
	private TeacherRepository teacherRepository;

	/**
	 * // 講師情報取得
	 * 
	 * @return json 整形した講師情報
	 *
	 */
	public List<Map<String, Object>> searchTeacher(final int classroomId, final String teacherName) {

		List<Teacher> teachers = new ArrayList<>();
		if (teacherName.isBlank()) {
			//教室単位で検索
			teachers = teacherRepository.findByClassroomAndDeleteFlg(new Classroom(classroomId), false);
		} else {
			//部分一致検索
			StringBuilder teacherSearchbuilder = new StringBuilder();
			teacherSearchbuilder.append("%").append(teacherName).append("%");
			teachers = teacherRepository.findByClassroomAndTeacherNameLikeAndDeleteFlg(new Classroom(classroomId),
					teacherSearchbuilder.toString(), false);
		}
		return pickUpInfo(teachers);
	}

	private List<Map<String, Object>> pickUpInfo(List<Teacher> teachers) {
		List<Map<String, Object>> returnJsonLiteral = new ArrayList<>();
		for (Teacher teacher : teachers) {
			Map<String, Object> teacherMap = new LinkedHashMap<>();
			teacherMap.put("teacherId", teacher.getId());//講師ID
			teacherMap.put("teacherName", teacher.getTeacherName());//講師名
			returnJsonLiteral.add(teacherMap);
		}
		return Collections.unmodifiableList(returnJsonLiteral);

	}

}
