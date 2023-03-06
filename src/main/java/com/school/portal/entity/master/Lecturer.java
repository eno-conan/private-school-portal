package com.school.portal.entity.master;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.school.portal.entity.StudentScheduleNormal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "m_lecturer")
@NoArgsConstructor
@Data
public class Lecturer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "classroom_id")
	@JsonIgnore
	private Classroom classroom;

	@Column(name = "name", length = 128, nullable = false, unique = true)
	private String lecturerName;

	@Column(name = "birthday")
	private Date birthday;

	@Column(name = "delete_flg")
	private boolean deleteFlg;

	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

//	@OneToMany(mappedBy = "lecturer", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = false)
//	private List<StudentSubject> studentSubjects;

	@OneToMany(mappedBy = "lecturer", cascade = CascadeType.PERSIST, orphanRemoval = false)
	private List<StudentScheduleNormal> studentScheduleNormal;

//	@OneToMany(mappedBy = "lecturer", cascade = CascadeType.PERSIST, orphanRemoval = false)
//	private List<LecturerTeachSubject> lecturerTeachSubject;
//
//	@OneToMany(mappedBy = "lecturer", cascade = CascadeType.PERSIST, orphanRemoval = false)
//	private List<LecturerWorkableTime> lecturerWorkableTime;
//
//	@OneToMany(mappedBy = "lecturer", cascade = CascadeType.PERSIST, orphanRemoval = false)
//	private List<StudentScheduleSpecial> studentScheduleSpecial;

	public Lecturer(Integer id) {
		this.id = id;
	}

	public Lecturer(Classroom classroom, String lecturerName, Date birthday, boolean deleteFlg, Timestamp createdAt,
			Timestamp updatedAt) {
		this.classroom = classroom;
		this.lecturerName = lecturerName;
		this.birthday = birthday;
		this.deleteFlg = deleteFlg;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

}
