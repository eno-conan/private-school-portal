//
//
package com.school.portal.classSchedule;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.school.portal.entity.StudentScheduleNormal;
import com.school.portal.entity.master.Student;
import com.school.portal.repository.StudentScheduleNormalRepository;

@Service
class TargetStudentClassesService {

  @Autowired
  StudentScheduleNormalRepository studentScheduleNormalRepository;

  /**
   * // チェックをいれた授業の生徒の授業予定を取得
   * 
   * @param content 予定取得に必要な情報
   * @return json 授業予定
   *
   */
  Map<String, Object> getTargeStudentClassSchedule(final String studentId) {
    List<StudentScheduleNormal> studentSchedule = studentScheduleNormalRepository
        .findByStudentOrderByClassDateAsc(new Student(Integer.parseInt(studentId)));
    return pickUpInfo(studentSchedule);
  }

  // 画面表示用に、Map生成
  private Map<String, Object> pickUpInfo(List<StudentScheduleNormal> studentSchedule) {
    Map<String, Object> result = new LinkedHashMap<>();
    List<Map<String, Object>> classList = new ArrayList<>();

    if (!studentSchedule.isEmpty()) {
      result.put("studentName", studentSchedule.get(0).getStudent().getStudentName());// 生徒名
      result.put("grade", studentSchedule.get(0).getStudent().getGrade().getDisplayName());// 学年
    }

    for (StudentScheduleNormal eachClass : studentSchedule) {
      Map<String, Object> infoMap = new LinkedHashMap<>();
      infoMap.put("id", eachClass.getId());// 授業ID
      infoMap.put("classDate", eachClass.getClassDate().toString());// 授業日
      infoMap.put("period", eachClass.getTimeTableNormal().getPeriod());// コマ
      infoMap.put("subject", eachClass.getSubject().getDisplayName());// 授業ID
      infoMap.put("teacherName", eachClass.getTeacher().getTeacherName());// 授業ID

      classList.add(infoMap);
      result.put("schedule", classList);
    }
    return result;
  }
}
