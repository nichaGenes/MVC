package com.courseregistration.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.Email;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {
    
    @Id
    @Column(name = "student_id", length = 8)
    @Pattern(regexp = "^69\\d{6}$", message = "Student ID must be 8 digits starting with '69'")
    private String studentId;
    
    @NotBlank(message = "Title is required")
    @Size(max = 10, message = "Title must not exceed 10 characters")
    private String title;
    
    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    @Column(name = "first_name")
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    @Column(name = "last_name")
    private String lastName;
    
    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @NotBlank(message = "Current school is required")
    @Size(max = 100, message = "Current school must not exceed 100 characters")
    @Column(name = "current_school")
    private String currentSchool;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;
    
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Registration> registrations = new ArrayList<>();
    
    // Constructors
    public Student() {}
    
    public Student(String studentId, String title, String firstName, String lastName, 
                   LocalDate dateOfBirth, String currentSchool, String email) {
        this.studentId = studentId;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.currentSchool = currentSchool;
        this.email = email;
    }
    
    // Getters and Setters
    public String getStudentId() {
        return studentId;
    }
    
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getCurrentSchool() {
        return currentSchool;
    }
    
    public void setCurrentSchool(String currentSchool) {
        this.currentSchool = currentSchool;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public List<Registration> getRegistrations() {
        return registrations;
    }
    
    public void setRegistrations(List<Registration> registrations) {
        this.registrations = registrations;
    }
    
    // Helper methods
    public int getAge() {
        return LocalDate.now().getYear() - dateOfBirth.getYear();
    }
    
    public String getFullName() {
        return title + " " + firstName + " " + lastName;
    }
    
    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", title='" + title + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", currentSchool='" + currentSchool + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
