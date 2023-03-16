// -----------------------------------------------------------------------------
// RegistStudentController.java
// -----------------------------------------------------------------------------
//
// 本ファイルについて
// * prepareDataClassroomRegistStudent()：生徒登録時に教室情報が必要。
// その情報をm_classroomテーブルから取得
// ** METHOD:GET
// ** Path：/student/register/prepare-classroom
//
// * registerStudent()：生徒の情報をm_studentテーブルに登録する
// ** METHOD:POST
// ** Path：/student/register
//
package com.school.portal.student.register;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.school.portal.model.RegisterStudentModel;
import jakarta.validation.Valid;

@RestController
class RegisterStudentController {

  @Autowired
  private RegisterStudentService registStudentService;

  /**
   * 生徒登録に必要なデータ取得（教室情報）
   * 
   * @return 教室一覧
   */
  @GetMapping("/student/register/prepare-classroom")
  List<Map<String, Object>> prepareDataClassroomRegistStudent() {
    try {
      return registStudentService.prepareClassroomData();
    } catch (Exception e) {
      return null;
    }

  }

  /**
   * 生徒登録
   * 
   * @param 登録する生徒の情報
   */
  @PostMapping("/student/register")
  String registerStudent(@RequestBody @Valid RegisterStudentModel content) {
    try {
      return registStudentService.registerStudent(content);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return "";
    }
  }
}
