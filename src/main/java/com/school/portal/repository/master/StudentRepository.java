package com.school.portal.repository.master;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.portal.entity.master.Classroom;
import com.school.portal.entity.master.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

	public List<Student> findAll();
//	
//	public Optional<List<Student>> findByIdAndDeleteFlgFalse(Integer studentId);
//
//	public List<Student> findByStudentNameLike(String studentName);
//	
	public List<Student> findByClassroomAndStudentNameLike(Classroom classroom, String studentName);

}
