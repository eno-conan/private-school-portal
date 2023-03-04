package com.school.portal.entity.master;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "m_grade")
@NoArgsConstructor
@Data
public class Grade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String gradeKey;

	@Column(name = "grade_division", length = 32, nullable = false)
	private String gradeDivision;

	@Column(name = "display_name", length = 32, nullable = false)
	private String displayName;
	
	@OneToMany(mappedBy = "grade", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<Student> students;
	
	@OneToMany(mappedBy = "grade", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<SubjectTargetGrade> subjectTargetGrade;

	public Grade(String gradeKey) {
		this.gradeKey = gradeKey;
	}
	
}
