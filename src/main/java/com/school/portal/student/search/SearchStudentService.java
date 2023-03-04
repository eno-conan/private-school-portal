package com.school.portal.student.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
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
	 * @throws RegistStudentException
	 * @throws JsonProcessingException
	 *
	 */
	public String searchStudent(final int classroomId, final String studentName) {

		// 結果格納
		List<Student> students = studentRepository.findAll();
		if (students.isEmpty()) {
			return "0";
		} else {
			return "1";

		}
	}
	//		List<Student> studentWholeInfo = new ArrayList<>();
	//		//教室名を条件に設定しているかどうかで取得方法を変える
	//		if ("".equals(classroomId)) {
	//			studentWholeInfo = studentRepository.findByStudentNameLike("%" + studentName + "%");
	//		} else {
	//			studentWholeInfo = studentRepository.findByClassroomAndStudentNameLike(
	//					new Classroom(Integer.parseInt(classroomId)), "%" + studentName + "%");
	//		}
	//		if (studentWholeInfo.isEmpty()) {
	//			log.info("生徒情報が0件の状態");
	//			return UseOverFunction.getDataToJsonFormat("0");
	//
	//		}
	//
	////		// 教室IDと教室名のみ取得
	//		List<Map<String, Object>> studentIdAndNameList = pickupstudentInfo(
	//				Collections.unmodifiableList(studentWholeInfo));
	//		String strJson = UseOverFunction.getDataToJsonFormat(studentIdAndNameList);
	//		return strJson;
	//	}
	//
	//	private List<Map<String, Object>> pickupstudentInfo(List<Student> studentWholeInfo) {
	//		List<Map<String, Object>> returnJsonLiteral = new ArrayList<>();
	//		for (Student info : studentWholeInfo) {
	//			Map<String, Object> infoMap = new LinkedHashMap<>();
	//			infoMap.put("studentId", info.getId());
	//			infoMap.put("studentName", info.getStudentName());
	//			infoMap.put("classroomName", info.getClassroom().getClassroomName());
	//			infoMap.put("prefectureName", info.getClassroom().getMPrefecture().getPrefectureName());
	//			returnJsonLiteral.add(infoMap);
	//		}
	//		return Collections.unmodifiableList(returnJsonLiteral);

}
