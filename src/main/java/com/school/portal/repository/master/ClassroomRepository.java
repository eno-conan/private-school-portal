package com.school.portal.repository.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.portal.entity.master.Classroom;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Integer>{
	
//	public List<Optional<StudentScheduleSpecial>> findAll();

}
