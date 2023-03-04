package com.school.portal.student.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.portal.entity.master.Student;
import com.school.portal.repository.master.StudentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SearchStudentService {

	@Autowired
	private StudentRepository studentRepository;

	/**
	 * // 生徒情報取得
	 * 
	 * @return json 整形した学年情報
	 *
	 */
	public List<Map<String, Object>> searchStudent(final int classroomId, final String studentName) {

		// 結果格納
		List<Student> students = studentRepository.findAll();
		if (students.isEmpty()) {
			return null;
		} else {
			return pickUpDisplayInfo(students);

		}
	}
	
	
		private List<Map<String, Object>> pickUpDisplayInfo(List<Student> students) {
			List<Map<String, Object>> returnJsonLiteral = new ArrayList<>();
			for (Student student : students) {
				Map<String, Object> studentMap = new LinkedHashMap<>();
				studentMap.put("studentId", student.getId());
				studentMap.put("studentName", student.getStudentName());
				studentMap.put("classroomName", student.getClassroom().getClassroomName());
				studentMap.put("prefectureName", student.getClassroom().getMPrefecture().getPrefectureName());
				returnJsonLiteral.add(studentMap);
			}
			return Collections.unmodifiableList(returnJsonLiteral);
	
}
		
		// 取得データをJson形式に
		public static String getDataToJsonFormat(Object returnJsonLiteral) throws JsonProcessingException {
			ObjectMapper mapper = new ObjectMapper();
			String strJson = "";
			strJson = mapper.writeValueAsString(returnJsonLiteral);
			return strJson;
		}
		
	//		List<Student> studentWholestudent = new ArrayList<>();
	//		//教室名を条件に設定しているかどうかで取得方法を変える
	//		if ("".equals(classroomId)) {
	//			studentWholestudent = studentRepository.findByStudentNameLike("%" + studentName + "%");
	//		} else {
	//			studentWholestudent = studentRepository.findByClassroomAndStudentNameLike(
	//					new Classroom(Integer.parseInt(classroomId)), "%" + studentName + "%");
	//		}
	//		if (studentWholestudent.isEmpty()) {
	//			log.student("生徒情報が0件の状態");
	//			return UseOverFunction.getDataToJsonFormat("0");
	//
	//		}
	//
	////		// 教室IDと教室名のみ取得
	//		List<Map<String, Object>> studentIdAndNameList = pickupstudentstudent(
	//				Collections.unmodifiableList(studentWholestudent));
	//		String strJson = UseOverFunction.getDataToJsonFormat(studentIdAndNameList);
	//		return strJson;
	//	}

}
