package com.courseregistration.dto;

import javax.validation.constraints.NotNull;

public class RegistrationRequestDTO {
    
    @NotNull(message = "Student ID is required")
    private String studentId;
    
    @NotNull(message = "Subject ID is required")
    private String subjectId;
    
    // Constructors
    public RegistrationRequestDTO() {}
    
    public RegistrationRequestDTO(String studentId, String subjectId) {
        this.studentId = studentId;
        this.subjectId = subjectId;
    }
    
    // Getters and Setters
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
}
