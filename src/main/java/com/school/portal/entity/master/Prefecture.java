package com.school.portal.entity.master;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

@Entity
@Table(name = "m_prefecture")
@Data
public class Prefecture {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "area_id")
	@JsonIgnore
	private Area mArea;

	@Column(name="prefecture_name",length = 128, nullable = false, unique = true)
	private String prefectureName;

	@Column(name="created_at")
	private Timestamp createdAt;

	@Column(name="updated_at")
	private Timestamp updatedAt;
	
	@OneToMany(mappedBy = "mPrefecture", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<Classroom> classrooms;

}
