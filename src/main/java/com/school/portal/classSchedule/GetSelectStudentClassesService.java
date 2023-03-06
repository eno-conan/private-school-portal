package com.school.portal.classSchedule;

import org.springframework.stereotype.Service;

@Service
class GetSelectStudentClassesService {

//	@Autowired
//	private com.school.portal.repository.StudentScheduleNormalRepository studentScheduleNormalRepository;
//
//	/**
//	 * // チェックをいれた授業の生徒の授業予定を取得
//	 * 
//	 * @param content 予定取得に必要な情報
//	 * @return json 授業予定
//	 * @throws JsonProcessingException
//	 *
//	 */
//	String getSelectStudentClassSchedule(final String studentId) throws JsonProcessingException {
//		Integer idToInt = Integer.parseInt(studentId);
//		List<StudentScheduleNormal> studentSchedule = studentScheduleNormalRepository
//				.findByStudentAndClassDateAfterOrderByClassDateAsc(new Student(idToInt), new Date());
//		List<Map<String, Object>> returnJsonLiteral = prepareStudentScheduleInfo(studentSchedule);
//		String strJson = UseOverFunction.getDataToJsonFormat(returnJsonLiteral);
//		return strJson;
//	}
//
//	// 生徒予定表示用に整形
//	private List<Map<String, Object>> prepareStudentScheduleInfo(List<StudentScheduleNormal> studentSchedule) {
//		List<Map<String, Object>> returnJsonLiteral = setEachClassInfoToMap(studentSchedule);
//		return Collections.unmodifiableList(returnJsonLiteral);
//	}
//
//	// 画面表示用に、Map生成
//	private List<Map<String, Object>> setEachClassInfoToMap(List<StudentScheduleNormal> studentSchedule) {
//		List<Map<String, Object>> returnJsonLiteral = new ArrayList<>();
//		for (StudentScheduleNormal eachClass : studentSchedule) {
//			Map<String, Object> eachRowInfoMap = new LinkedHashMap<>();
//			// TODO:振替授業である場合に、その情報を画面に表示しておいた方がいいかと・・
//			// 理想は、いつからの振替かだが、振替期限から分かるか
//			eachRowInfoMap.put("id", String.valueOf(eachClass.getId()));
//			eachRowInfoMap.put("period", eachClass.getTimeTableNormal().getPeriod());
//			eachRowInfoMap.put("grade", eachClass.getStudent().getGrade().getDisplayName());
//			eachRowInfoMap.put("subject", eachClass.getSubject().getDisplayName());
//			eachRowInfoMap.put("studentId", String.valueOf(eachClass.getStudent().getId()));
//			eachRowInfoMap.put("studentName", eachClass.getStudent().getStudentName());
//			eachRowInfoMap.put("lecturerName", eachClass.getLecturer().getLecturerName());
//
//			String classDate = UseOverFunction.dateToDateStr(eachClass.getClassDate());
//			eachRowInfoMap.put("classDate", classDate.replace("-", "/"));
//			returnJsonLiteral.add(eachRowInfoMap);
//		}
//		return returnJsonLiteral;
//	}
}
