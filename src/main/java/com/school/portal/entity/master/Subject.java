package com.school.portal.entity.master;

import java.sql.Timestamp;
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
@Table(name = "m_subject")
@NoArgsConstructor
@Data
public class Subject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String subjectKey;

	@Column(name = "subject_division", length = 32, nullable = false)
	private String subjectDivision;

	@Column(name = "display_name", length = 32, nullable = false)
	private String displayName;

	@Column(name = "delete_flg", length = 2)
	private boolean deleteFlg;

	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

//	@OneToMany(mappedBy = "subject", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = false)
//	@JsonIgnore
//	private List<StudentSubject> studentSubjects;
//
//	@OneToMany(mappedBy = "subject", cascade = CascadeType.PERSIST, orphanRemoval = false)
//	@JsonIgnore
//	private List<StudentScheduleNormal> studentScheduleNormal;
//
//	@OneToMany(mappedBy = "subject", cascade = CascadeType.PERSIST, orphanRemoval = false)
//	@JsonIgnore
//	private List<LecturerTeachSubject> lecturerTeachSubject;
//
//	@OneToMany(mappedBy = "subject", cascade = CascadeType.MERGE, orphanRemoval = false)
//	@JsonIgnore
//	private List<StudentScheduleSpecial> studentScheduleSpecial;
//
//	@OneToMany(mappedBy = "subject", cascade = CascadeType.PERSIST, orphanRemoval = false)
//	@JsonIgnore
//	private List<StudentClassSpecialSummary> studentClassSpecialSummary;

	@OneToMany(mappedBy = "subject", cascade = CascadeType.PERSIST, orphanRemoval = false)
	private List<SubjectTargetGrade> subjectTargetGrade;

	public Subject(String subjectKey) {
		this.subjectKey = subjectKey;
	}

}
