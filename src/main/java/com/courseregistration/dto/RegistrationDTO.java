package com.courseregistration.dto;

import javax.validation.constraints.NotNull;
import com.courseregistration.model.RegistrationStatus;

import java.time.LocalDateTime;

public class RegistrationDTO {
    
    private Long id;
    
    @NotNull(message = "Student ID is required")
    private String studentId;
    
    @NotNull(message = "Subject ID is required")
    private String subjectId;
    
    private LocalDateTime registrationDate;
    
    private RegistrationStatus status = RegistrationStatus.ACTIVE;
    
    // Additional fields for response
    private String studentName;
    private String subjectName;
    
    // Constructors
    public RegistrationDTO() {}
    
    public RegistrationDTO(String studentId, String subjectId) {
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.registrationDate = LocalDateTime.now();
        this.status = RegistrationStatus.ACTIVE;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getStudentId() {
        return studentId;
    }
    
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    
    public String getSubjectId() {
        return subjectId;
    }
    
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
    
    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }
    
    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }
    
    public RegistrationStatus getStatus() {
        return status;
    }
    
    public void setStatus(RegistrationStatus status) {
        this.status = status;
    }
    
    public String getStudentName() {
        return studentName;
    }
    
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    
    public String getSubjectName() {
        return subjectName;
    }
    
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
