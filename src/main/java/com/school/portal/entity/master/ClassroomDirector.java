package com.school.portal.entity.master;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "classroom_director")
@Data
public class ClassroomDirector {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne
	@JoinColumn(name = "classroom_id")
	@JsonIgnore
	private Classroom classroom;
	
	@OneToOne
	@JoinColumn(name="employee_id")
	@JsonIgnore
	private Employee employee;
	
	@Column(name="created_at")
	private Timestamp createdAt;

	@Column(name="updated_at")
	private Timestamp updatedAt;

	public ClassroomDirector(Classroom classroom, Employee employee, Timestamp createdAt, Timestamp updatedAt) {
		this.classroom = classroom;
		this.employee = employee;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
}
