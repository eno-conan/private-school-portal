// -----------------------------------------------------------------------------
// SearchStudentService.java
// -----------------------------------------------------------------------------
//
// 本ファイルについて
// * searchStudent(final int classroomId, final String studentName)
// m_studentテーブルから、教室ID・生徒名を条件に情報を取得
//
// * pickUpDisplayInfo():m_classroomテーブルから取得した情報のうち、
// 画面表示に必要な情報を抽出
//
package com.school.portal.student.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.school.portal.entity.master.Classroom;
import com.school.portal.entity.master.Student;
import com.school.portal.exception.StudentSearchException;
import com.school.portal.repository.master.StudentRepository;

@Service
// @Slf4j
class SearchStudentService {

  @Autowired
  private StudentRepository studentRepository;

  /**
   * // 生徒情報取得
   * 
   * @param classroomId
   * @param studentName
   * 
   * @return json 整形した生徒情報
   * @throws StudentSearchException
   *
   */
  public List<Map<String, Object>> searchStudent(final int classroomId, final String studentName)
      throws StudentSearchException {

    List<Student> students = new ArrayList<>();

    // classroomId：教室数より大きい場合は×
    if (studentName.isEmpty()) {
      students = studentRepository.findAll();
    } else {
      // ここはフィルタリングしたデータ取得
      StringBuilder builder = new StringBuilder();
      builder.append("%").append(studentName).append("%");
      // 部分一致
      students = studentRepository.findByClassroomAndStudentNameLike(new Classroom(classroomId),
          builder.toString());
    }

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

}
