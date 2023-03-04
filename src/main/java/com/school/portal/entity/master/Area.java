package com.school.portal.entity.master;

import java.sql.Timestamp;
import java.util.ArrayList;
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

@Entity
@Table(name = "m_area")
@Data
public class Area {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="area_name",length = 128, nullable = false, unique = true)
	private String areaName;

	@Column(name="created_at")
	private Timestamp createdAt;

	@Column(name="updated_at")
	private Timestamp updatedAt;

	@OneToMany(mappedBy = "mArea", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<Prefecture> prefectures = new ArrayList<>();

}
