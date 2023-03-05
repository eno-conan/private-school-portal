package com.school.portal.student.register;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.school.portal.entity.master.Grade;
import com.school.portal.repository.master.GradeRepository;
import com.school.portal.util.UseOverFunction;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
class PrepareService {

	@Autowired
	private GradeRepository gradeRepository;

	/**
	 * // 学年情報取得
	 * 
	 * @return json 整形した学年情報
	 * @throws RegistStudentException
	 * @throws JsonProcessingException
	 *
	 */
	String prepareDataGradeRegistStudent() throws JsonProcessingException {
		List<Grade> grades = gradeRepository.findAll();
		if (grades.isEmpty()) {
			log.error("学年情報が0件の状態");
			//			throw new RegistStudentException("学年情報取得でエラーが発生しました。少々お待ちください。");
		}

		List<Map<String, String>> gradekeyAndNameList = pickupGradeInfo(grades);
		String strJson = UseOverFunction.getDataToJsonFormat(gradekeyAndNameList);
		return strJson;
	}

	private List<Map<String, String>> pickupGradeInfo(List<Grade> grades) {
		List<Map<String, String>> returnJsonLiteral = new ArrayList<>();
		for (Grade info : grades) {
			Map<String, String> gradekeyAndNameMap = new LinkedHashMap<>();
			gradekeyAndNameMap.put("gradeKey", info.getGradeKey());
			gradekeyAndNameMap.put("gradeName", info.getDisplayName());
			returnJsonLiteral.add(gradekeyAndNameMap);
		}
		return Collections.unmodifiableList(returnJsonLiteral);
	}

}
