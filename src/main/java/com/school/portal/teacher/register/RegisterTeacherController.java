// -----------------------------------------------------------------------------
// RegisterTeacherController.java
// -----------------------------------------------------------------------------
//
// 本ファイルについて
// * prepareDataClassroomRegistStudent()：生徒登録時に教室情報が必要。
// その情報をm_classroomテーブルから取得
// ** METHOD:GET
// ** Path：/teacher/register/prepare-classroom
//
// * registerTeacher()：講師の情報をm_teacherテーブルに登録する
// ** METHOD:POST
// ** Path：/teacher/register
//
package com.school.portal.teacher.register;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.school.portal.model.RegisterTeacherModel;
import jakarta.validation.Valid;

@RestController
public class RegisterTeacherController {

  @Autowired
  private RegisterTeacherService registerTeacherService;


  /**
   * 講師登録に必要なデータ取得（教室情報）
   */
  @GetMapping("/teacher/register/prepare-classroom")
  List<Map<String, Object>> prepareDataClassroomRegisterTeacher() {
    try {
      return registerTeacherService.prepareClassroomData();
    } catch (Exception e) {
      return null;
    }

  }

  /**
   * 講師登録
   * 
   * @param 登録情報
   * @return 成功・失敗の結果
   */
  @PostMapping("/teacher/register")
  String registerTeacher(@RequestBody @Valid RegisterTeacherModel content) {
    try {
      return registerTeacherService.registerTeacher(content);
    } catch (Exception e) {
      return null;
    }

  }

}
