package com.courseregistration.repository;

import com.courseregistration.model.Registration;
import com.courseregistration.model.RegistrationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    
    List<Registration> findByStudentId(String studentId);
    
    List<Registration> findBySubjectId(String subjectId);
    
    List<Registration> findByStatus(RegistrationStatus status);
    
    @Query("SELECT r FROM Registration r WHERE r.studentId = :studentId AND r.status = 'ACTIVE'")
    List<Registration> findActiveRegistrationsByStudentId(@Param("studentId") String studentId);
    
    @Query("SELECT r FROM Registration r WHERE r.subjectId = :subjectId AND r.status = 'ACTIVE'")
    List<Registration> findActiveRegistrationsBySubjectId(@Param("subjectId") String subjectId);
    
    @Query("SELECT COUNT(r) FROM Registration r WHERE r.subjectId = :subjectId AND r.status = 'ACTIVE'")
    Long countActiveRegistrationsBySubjectId(@Param("subjectId") String subjectId);
    
    @Query("SELECT COUNT(r) FROM Registration r WHERE r.studentId = :studentId AND r.status = 'ACTIVE'")
    Long countActiveRegistrationsByStudentId(@Param("studentId") String studentId);
    
    Optional<Registration> findByStudentIdAndSubjectIdAndStatus(String studentId, String subjectId, RegistrationStatus status);
    
    boolean existsByStudentIdAndSubjectIdAndStatus(String studentId, String subjectId, RegistrationStatus status);
    
    @Query("SELECT r FROM Registration r WHERE r.studentId = :studentId AND r.subject.subjectId = :subjectId")
    List<Registration> findByStudentIdAndSubjectId(@Param("studentId") String studentId, @Param("subjectId") String subjectId);
}
