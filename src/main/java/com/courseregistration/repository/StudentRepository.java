package com.courseregistration.repository;

import com.courseregistration.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    
    Optional<Student> findByEmail(String email);
    
    List<Student> findByCurrentSchool(String currentSchool);
    
    @Query("SELECT s FROM Student s WHERE s.firstName LIKE %:name% OR s.lastName LIKE %:name%")
    List<Student> findByNameContaining(@Param("name") String name);
    
    @Query("SELECT s FROM Student s WHERE s.dateOfBirth >= :minDate")
    List<Student> findByAgeGreaterThan(@Param("minDate") java.time.LocalDate minDate);
    
    boolean existsByEmail(String email);
}
