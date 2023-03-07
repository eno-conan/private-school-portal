package com.school.portal.repository.master;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.portal.entity.master.Classroom;
import com.school.portal.entity.master.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

	//特定教室の在職中の講師一覧	
	public List<Teacher> findByClassroomAndDeleteFlg(Classroom classroom, boolean deleteFlg);

	//特定教室のある講師（同じ苗字が複数人いる可能性あり）
	public List<Teacher> findByClassroomAndTeacherNameLikeAndDeleteFlg(Classroom classroom, String teacherName,
			boolean deleteFlg);

}
