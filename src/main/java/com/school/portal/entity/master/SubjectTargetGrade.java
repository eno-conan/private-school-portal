package com.school.portal.entity.master;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "m_subject_target_grade")
@NoArgsConstructor
@Data
public class SubjectTargetGrade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "subject_key", nullable = false)
	@JsonIgnore
	private Subject subject;
	
	@ManyToOne
	@JoinColumn(name = "grade_key", nullable = false)
	@JsonIgnore
	private Grade grade;
	
}
