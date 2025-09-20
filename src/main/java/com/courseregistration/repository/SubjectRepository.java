package com.courseregistration.repository;

import com.courseregistration.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, String> {
    
    List<Subject> findByInstructorName(String instructorName);
    
    List<Subject> findByCredits(Integer credits);
    
    @Query("SELECT s FROM Subject s WHERE s.maxCapacity = -1 OR s.currentEnrollment < s.maxCapacity")
    List<Subject> findAvailableSubjects();
    
    @Query("SELECT s FROM Subject s WHERE s.prerequisiteSubjectId IS NULL")
    List<Subject> findSubjectsWithoutPrerequisites();
    
    @Query("SELECT s FROM Subject s WHERE s.prerequisiteSubjectId = :prerequisiteId")
    List<Subject> findByPrerequisiteSubjectId(@Param("prerequisiteId") String prerequisiteId);
    
    @Query("SELECT s FROM Subject s WHERE s.subjectId LIKE '0550%'")
    List<Subject> findFacultyCourses();
    
    @Query("SELECT s FROM Subject s WHERE s.subjectId LIKE '9069%'")
    List<Subject> findGeneralEducationCourses();
    
    @Query("SELECT s FROM Subject s WHERE s.subjectName LIKE %:name%")
    List<Subject> findBySubjectNameContaining(@Param("name") String name);
    
    @Query("SELECT s FROM Subject s WHERE s.currentEnrollment < s.maxCapacity OR s.maxCapacity = -1")
    List<Subject> findSubjectsWithAvailableCapacity();
}
