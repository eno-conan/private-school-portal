package com.school.portal.entity.master;

import java.sql.Timestamp;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@jakarta.persistence.Entity
@Table(name = "m_employee")
@Data
@NoArgsConstructor
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "classroom_id")
	@JsonIgnore
	private Classroom classroom;
	
	@Column(name="name",length = 128, nullable = false, unique = true)
	private String employeeName;
	
	@Column(name="birthday")
	private Date birthday;
	
	@Column(name="delete_flg",length = 2)
	private boolean deleteFlg;

	@Column(name="created_at")
	private Timestamp createdAt;

	@Column(name="updated_at")
	private Timestamp updatedAt;

	public Employee(Integer id) {
		this.id = id;
	}
	
	

}
