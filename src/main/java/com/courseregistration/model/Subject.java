package com.courseregistration.model;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subjects")
public class Subject {
    
    @Id
    @Column(name = "subject_id", length = 8)
    @Pattern(regexp = "^(0550|9069)\\d{4}$", message = "Subject ID must be 8 digits starting with '0550' or '9069'")
    private String subjectId;
    
    @NotBlank(message = "Subject name is required")
    @Size(max = 100, message = "Subject name must not exceed 100 characters")
    @Column(name = "subject_name")
    private String subjectName;
    
    @NotNull(message = "Credits is required")
    @Min(value = 1, message = "Credits must be greater than 0")
    private Integer credits;
    
    @NotBlank(message = "Instructor name is required")
    @Size(max = 100, message = "Instructor name must not exceed 100 characters")
    @Column(name = "instructor_name")
    private String instructorName;
    
    @Column(name = "prerequisite_subject_id", length = 8)
    private String prerequisiteSubjectId;
    
    @NotNull(message = "Max capacity is required")
    @Min(value = -1, message = "Max capacity must be -1 for unlimited or a positive number")
    @Column(name = "max_capacity")
    private Integer maxCapacity;
    
    @NotNull(message = "Current enrollment is required")
    @Min(value = 0, message = "Current enrollment cannot be negative")
    @Column(name = "current_enrollment")
    private Integer currentEnrollment = 0;
    
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Registration> registrations = new ArrayList<>();
    
    // Constructors
    public Subject() {}
    
    public Subject(String subjectId, String subjectName, Integer credits, String instructorName, 
                   String prerequisiteSubjectId, Integer maxCapacity) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.credits = credits;
        this.instructorName = instructorName;
        this.prerequisiteSubjectId = prerequisiteSubjectId;
        this.maxCapacity = maxCapacity;
        this.currentEnrollment = 0;
    }
    
    // Getters and Setters
    public String getSubjectId() {
        return subjectId;
    }
    
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
    
    public String getSubjectName() {
        return subjectName;
    }
    
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    
    public Integer getCredits() {
        return credits;
    }
    
    public void setCredits(Integer credits) {
        this.credits = credits;
    }
    
    public String getInstructorName() {
        return instructorName;
    }
    
    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }
    
    public String getPrerequisiteSubjectId() {
        return prerequisiteSubjectId;
    }
    
    public void setPrerequisiteSubjectId(String prerequisiteSubjectId) {
        this.prerequisiteSubjectId = prerequisiteSubjectId;
    }
    
    public Integer getMaxCapacity() {
        return maxCapacity;
    }
    
    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
    
    public Integer getCurrentEnrollment() {
        return currentEnrollment;
    }
    
    public void setCurrentEnrollment(Integer currentEnrollment) {
        this.currentEnrollment = currentEnrollment;
    }
    
    public List<Registration> getRegistrations() {
        return registrations;
    }
    
    public void setRegistrations(List<Registration> registrations) {
        this.registrations = registrations;
    }
    
    // Helper methods
    public boolean isAvailable() {
        return maxCapacity == -1 || currentEnrollment < maxCapacity;
    }
    
    public boolean hasPrerequisite() {
        return prerequisiteSubjectId != null && !prerequisiteSubjectId.trim().isEmpty();
    }
    
    public String getCourseType() {
        return subjectId.startsWith("0550") ? "Faculty Course" : "General Education Course";
    }
    
    @Override
    public String toString() {
        return "Subject{" +
                "subjectId='" + subjectId + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", credits=" + credits +
                ", instructorName='" + instructorName + '\'' +
                ", prerequisiteSubjectId='" + prerequisiteSubjectId + '\'' +
                ", maxCapacity=" + maxCapacity +
                ", currentEnrollment=" + currentEnrollment +
                '}';
    }
}
