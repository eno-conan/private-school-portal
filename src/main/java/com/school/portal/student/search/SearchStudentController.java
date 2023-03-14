// -----------------------------------------------------------------------------
// RegistStudentController.java
// -----------------------------------------------------------------------------
//
// 本ファイルについて
// * searchStudent()：
// 教室ID・生徒名をQueryパラメータとして受け取り、該当する生徒情報をレスポンスとする
// ** METHOD:GET
// ** Path：/student/search
//
package com.school.portal.student.search;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.school.portal.exception.StudentSearchException;

@RestController
class SearchStudentController {

  @Autowired
  private SearchStudentService searchStudentService;

  /**
   * 生徒データ取得
   * 
   * @return 該当する生徒一覧
   */
  // http://localhost:8080/student/search?classroomId=1&studentName=Mike
  @GetMapping("/student/search")
  List<Map<String, Object>> searchStudent(@RequestParam(name = "classroomId") final int classroomId,
      @RequestParam(name = "studentName") final String studentName) {
    try {
      return searchStudentService.searchStudent(classroomId, studentName);
    } catch (StudentSearchException e) {
      e.printStackTrace();
      return null;
    }
  }

}
