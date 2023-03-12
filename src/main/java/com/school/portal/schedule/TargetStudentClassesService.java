// -----------------------------------------------------------------------------
// TargetStudentClassesService.java
// -----------------------------------------------------------------------------
//
// 本ファイルについて
// * getTargeStudentClassSchedule()：特定生徒の授業予定をデータベースから取得を行い、
// Controllerクラスへデータを返す。
//
// * pickUpInfo()：Controllerクラスに返すデータを整形する。
// 返すデータに必要な項目は、メソッドのコメントを確認
//
package com.school.portal.schedule;

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
   * getTargeStudentClassSchedule(final String studentId)
   * 
   * @param studentId 生徒ID
   * @return 授業予定を設定したMap
   *
   */
  Map<String, Object> getTargeStudentClassSchedule(final String studentId) {
    List<StudentScheduleNormal> studentSchedule = studentScheduleNormalRepository
        .findByStudentOrderByClassDateAsc(new Student(Integer.parseInt(studentId)));
    return pickUpInfo(studentSchedule);
  }

  /**
   * pickUpInfo
   *
   * @param studentSchedule
   * @return 授業予定に必要な項目（行末に項目名を記載）
   *
   */
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
