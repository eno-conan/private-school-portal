package com.school.portal.model;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistStudentModel implements Serializable {

	@NotBlank(message = "studentName is required.")
	private String studentName;

	@NotBlank(message = "birthDay is required.")
	private String birthDay;

	@NotBlank(message = "grade is required.")
	private String grade;

	@Positive
	private int classroomId;

}
