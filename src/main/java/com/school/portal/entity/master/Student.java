package com.school.portal.entity.master;

import java.sql.Timestamp;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
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
@Table(name = "m_student")
@NoArgsConstructor
@Data
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "classroom_id")
	@JsonIgnore
//	@JsonIdentityReference(alwaysAsId = true)
//	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	private Classroom classroom;

	@ManyToOne
	@JoinColumn(name = "grade_key")
	@JsonIgnore
//	@JsonIdentityReference(alwaysAsId = true)
//	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "gradeKey")
	private Grade grade;

	@Column(name = "name", length = 128, nullable = false, unique = true)
	private String studentName;

	@Column(name = "birthday")
	private Date birthday;

	@Column(name = "delete_flg", length = 2)
	private boolean deleteFlg;

	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

//	@OneToMany(mappedBy = "student", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = false)
//	private List<StudentSubject> studentSubjects;
//
//	@OneToMany(mappedBy = "student", cascade = CascadeType.PERSIST, orphanRemoval = false)
//	private List<StudentScheduleNormal> studentScheduleNormal;
//
//	@OneToMany(mappedBy = "student", cascade = CascadeType.PERSIST, orphanRemoval = false)
//	private List<StudentScheduleSpecial> studentScheduleSpecial;
//
//	@OneToMany(mappedBy = "student", cascade = CascadeType.PERSIST, orphanRemoval = false)
//	private List<StudentClassSpecialSummary> studentClassSpecialSummary;
//	
//	@OneToMany(mappedBy = "student", cascade = CascadeType.PERSIST, orphanRemoval = false)
//	private List<StudentAttendanceSpecial> studentAttendanceSpecial;


	public Student(Integer id) {
		this.id = id;
	}

//	public Student insertStudent() {
//		Student initState = new Student();
//		initState.setDeleteFlg(false);
//		initState.setCreatedAt(new Timestamp(System.currentTimeMillis()));
//		initState.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
//		return null;
//	}

}
