package com.courseregistration.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "registrations")
public class Registration {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Student ID is required")
    @Column(name = "student_id", length = 8)
    private String studentId;
    
    @NotNull(message = "Subject ID is required")
    @Column(name = "subject_id", length = 8)
    private String subjectId;
    
    @Column(name = "registration_date")
    private LocalDateTime registrationDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RegistrationStatus status = RegistrationStatus.ACTIVE;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    private Student student;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", insertable = false, updatable = false)
    private Subject subject;
    
    // Constructors
    public Registration() {
        this.registrationDate = LocalDateTime.now();
    }
    
    public Registration(String studentId, String subjectId) {
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
    
    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
    
    public Subject getSubject() {
        return subject;
    }
    
    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    
    @Override
    public String toString() {
        return "Registration{" +
                "id=" + id +
                ", studentId='" + studentId + '\'' +
                ", subjectId='" + subjectId + '\'' +
                ", registrationDate=" + registrationDate +
                ", status=" + status +
                '}';
    }
}
