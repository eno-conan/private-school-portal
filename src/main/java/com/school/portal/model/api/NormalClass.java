package com.school.portal.model.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class NormalClass {

  private int classId;
  private String targetDate;
  private String period;
  private String grade;
  private String subject;
  private int studentId;
  private String studentName;
  private String teacherName;

}
