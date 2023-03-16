package com.school.portal.model;

import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterTeacherModel implements Serializable {

  @NotBlank(message = "teacherName is required.")
  private String teacherName;

  @NotBlank(message = "birthDay is required.")
  private String birthDay;

  @NotBlank(message = "tellNumber is required.")
  @Size(max = 16, message = "tellNumber max Length is 16")
  private String tellNumber;

  @NotBlank(message = "address is required.")
  @Size(max = 256, message = "address max Length is 256")
  private String address;

  @NotBlank(message = "mailAddress is required.")
  @Size(max = 256, message = "mailAddress max Length is 256")
  private String mailAddress;

  @Positive
  private int classroomId;

}
