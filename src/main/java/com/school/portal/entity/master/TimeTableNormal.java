package com.school.portal.entity.master;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "time_table_normal")
@Data
@NoArgsConstructor
public class TimeTableNormal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	// 要らないかも
	@Column(name = "day_of_week", length = 16, nullable = false)
	private String dayOfWeek;

	@Column(name = "period", length = 16, nullable = false)
	private String period;

	@Column(name = "day_of_week_ja", length = 16, nullable = false)
	private String dayOfWeekJa;

//	@OneToMany(mappedBy = "timeTableNormal", cascade = { CascadeType.PERSIST,
//			CascadeType.MERGE }, orphanRemoval = false)
//	private List<StudentSubject> studentSubjects;
//
//	@OneToMany(mappedBy = "timeTableNormal", cascade = CascadeType.PERSIST, orphanRemoval = false)
//	private List<teacherWorkableTime> teacherWorkableTime;
//
//	@OneToMany(mappedBy = "timeTableNormal", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = false)
//	private List<StudentScheduleNormal> studentScheduleNormal;

	public TimeTableNormal(Integer id) {
		this.id = id;
	}

}
