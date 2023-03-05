package com.school.portal.repository.master;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.portal.entity.master.Grade;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer>{
	
	public List<Grade> findAll();

}
