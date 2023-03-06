package com.school.portal.entity;

import java.sql.Timestamp;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.school.portal.entity.master.Lecturer;
import com.school.portal.entity.master.Student;
import com.school.portal.entity.master.Subject;
import com.school.portal.entity.master.TimeTableNormal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "student_schedule_normal")
@NoArgsConstructor
@Data
public class StudentScheduleNormal implements Cloneable, StudentSchedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	@JsonIgnore
	private Student student;

	@ManyToOne
	@JoinColumn(name = "subject_key")
	@JsonIgnore
	private Subject subject;

	@ManyToOne
	@JoinColumn(name = "lecturer_id")
	@JsonIgnore
	private Lecturer lecturer;

	@ManyToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "time_table_normal_id", nullable = true)
	@JsonIgnore
	private TimeTableNormal timeTableNormal;

	@Column(name = "class_date")
	@Temporal(TemporalType.DATE)
	private Date classDate;

	@Column(name = "class_date_origin")
	@Temporal(TemporalType.DATE)
	private Date classDateOrigin;

	@Column(name = "reschedule_date_start")
	private Date rescheduleDateStart;

	@Column(name = "reschedule_date_last")
	private Date rescheduleDateLast;

	@Column(name = "reschedule_flg")
	private boolean rescheduleFlg;

	@Column(name = "status")
	private int status;

	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@Transient
	private String receiveErrorMessage;

	@Override
	public StudentScheduleNormal clone() {
		StudentScheduleNormal studentScheduleNormal = new StudentScheduleNormal();
		studentScheduleNormal.id = id;
		studentScheduleNormal.student = student;
		studentScheduleNormal.subject = subject;
		studentScheduleNormal.lecturer = lecturer;
		studentScheduleNormal.timeTableNormal = timeTableNormal;
		studentScheduleNormal.classDate = classDate;
		studentScheduleNormal.rescheduleDateStart = rescheduleDateStart;
		studentScheduleNormal.rescheduleDateLast = rescheduleDateLast;
		studentScheduleNormal.rescheduleFlg = rescheduleFlg;
		studentScheduleNormal.status = status;
		studentScheduleNormal.createdAt = createdAt;
		studentScheduleNormal.updatedAt = updatedAt;
		studentScheduleNormal.receiveErrorMessage = receiveErrorMessage;
		return studentScheduleNormal;
	}

	public StudentScheduleNormal(String message) {
		this.receiveErrorMessage = message;
	}

	public StudentScheduleNormal(Integer id) {
		this.id = id;
	}
}
