package com.school.portal.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.portal.entity.StudentScheduleNormal;

@Repository
public interface StudentScheduleNormalRepository extends JpaRepository<StudentScheduleNormal, Integer> {

	// 対象日付の全予定取得
	public List<StudentScheduleNormal> findAllByClassDateOrderByTimeTableNormalAsc(Date date);

//	// あるコマの講師の授業を取得
//	public List<StudentScheduleNormal> findAllByteacherAndClassDateAndTimeTableNormal(Teacher teacher, Date date,
//			TimeTableNormal timeTableNormal);
//
//	// あるコマの生徒の授業予定を取得
//	public Optional<StudentScheduleNormal> findByStudentAndClassDateAndTimeTableNormal(Student student, Date date,
//			TimeTableNormal timeTableNormal);
//
//	// 生徒の授業予定を取得
//	public List<StudentScheduleNormal> findByStudentAndClassDateAfterOrderByClassDateAsc(Student student, Date date);
//
//	// 実績漏れ授業の取得（授業日付が実行日より前で、statusが0のままのデータを取得）
//	public List<StudentScheduleNormal> findByStatusAndClassDateBefore(int Status, Date date);

}
