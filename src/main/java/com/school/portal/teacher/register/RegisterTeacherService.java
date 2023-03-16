// -----------------------------------------------------------------------------
// RegistStudentService.java
// -----------------------------------------------------------------------------
//
// 本ファイルについて
// * prepareClassroomData()：m_classroomテーブルから教室情報が必要。

// * registerTeacher()：講師の情報をm_teacherテーブルに登録する
//
// * pickupClassroomInfo():m_classroomテーブルから取得した情報のうち、
// 画面表示に必要な情報を抽出
//
package com.school.portal.teacher.register;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.school.portal.entity.master.Classroom;
import com.school.portal.entity.master.Teacher;
import com.school.portal.model.RegisterTeacherModel;
import com.school.portal.repository.master.ClassroomRepository;
import com.school.portal.repository.master.TeacherRepository;
import com.school.portal.util.UseOverFunction;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RegisterTeacherService {


  @Autowired
  private TeacherRepository teacherRepository;

  @Autowired
  private ClassroomRepository classroomRepository;

  /**
   * 講師登録
   * 
   * @param content 登録内容
   * @throws JsonProcessingException
   *
   */
  @Transactional
  String registerTeacher(final RegisterTeacherModel content) throws JsonProcessingException {
    Teacher teacher = new Teacher();
    teacher.setTeacherName(content.getTeacherName());
    teacher.setBirthday(UseOverFunction.convertStrDateToDateType(content.getBirthDay()));
    teacher.setClassroom(new Classroom(content.getClassroomId()));
    teacher.setTellNumber(content.getTellNumber());
    teacher.setAddress(content.getAddress());
    teacher.setMailAddress(content.getMailAddress());
    teacher.setDeleteFlg(false);
    teacher.setCreatedAt(new Timestamp(System.currentTimeMillis()));
    teacher.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
    teacherRepository.save(teacher);
    Teacher registerResult = teacherRepository.save(teacher);
    return registerResult.getTeacherName();
  }

  /**
   * // 教室情報取得
   * 
   * @return 整形した教室情報
   *
   */
  public List<Map<String, Object>> prepareClassroomData() {
    List<Classroom> classrooms = classroomRepository.findAll();
    if (classrooms.isEmpty()) {
      log.warn("教室情報0件");
    } else {
      log.info("教室情報1件以上");
    }

    // 教室IDと教室名のみ取得
    return pickupClassroomInfo(classrooms);
  }

  /**
   * // 教室情報取得（整形用）
   * 
   * @return 必要項目のみ設定した教室情報
   *
   */
  private List<Map<String, Object>> pickupClassroomInfo(List<Classroom> classrooms) {
    List<Map<String, Object>> returnJsonLiteral = new ArrayList<>();
    for (Classroom info : classrooms) {
      Map<String, Object> classroomIdAndNameMap = new LinkedHashMap<>();
      classroomIdAndNameMap.put("classroomId", info.getId());
      classroomIdAndNameMap.put("prefectureName", info.getMPrefecture().getPrefectureName());
      classroomIdAndNameMap.put("classroomName", info.getClassroomName());
      returnJsonLiteral.add(classroomIdAndNameMap);
    }
    return Collections.unmodifiableList(returnJsonLiteral);
  }



}
