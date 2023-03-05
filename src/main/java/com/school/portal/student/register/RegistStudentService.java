package com.school.portal.student.register;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.school.portal.entity.master.Classroom;
import com.school.portal.repository.master.ClassroomRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RegistStudentService {

	//	@Autowired
	//	private GradeRepository gradeRepository;

	@Autowired
	private ClassroomRepository classroomRepository;

	/**
	 * // 教室情報取得
	 * 
	 * @return json 整形した教室情報
	 * @throws RegistStudentException
	 * @throws JsonProcessingException
	 *
	 */
	public List<Map<String, Object>> prepareClassroomData() {
		List<Classroom> classrooms = classroomRepository.findAll();
		if (classrooms.isEmpty()) {
			log.error("教室情報0件");
		}

		// 教室IDと教室名のみ取得
		return pickupClassroomInfo(classrooms);
	}

	/**
	 * // 教室情報取得（整形用）
	 * 
	 * @return 必要項目のみ設定した教室情報
	 *
	 */
	private List<Map<String, Object>> pickupClassroomInfo(List<Classroom> classrooms) {
		List<Map<String, Object>> returnJsonLiteral = new ArrayList<>();
		for (Classroom info : classrooms) {
			Map<String, Object> classroomIdAndNameMap = new LinkedHashMap<>();
			classroomIdAndNameMap.put("classroomId", info.getId());
			classroomIdAndNameMap.put("prefectureName", info.getMPrefecture().getPrefectureName());
			classroomIdAndNameMap.put("classroomName", info.getClassroomName());
			returnJsonLiteral.add(classroomIdAndNameMap);
		}
		return Collections.unmodifiableList(returnJsonLiteral);
	}

}
