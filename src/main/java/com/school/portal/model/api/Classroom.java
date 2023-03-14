package com.school.portal.model.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Classroom {

  private int classroomId;
  private String prefectureName;
  private String classroomName;

}
