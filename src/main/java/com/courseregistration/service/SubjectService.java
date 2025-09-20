package com.courseregistration.service;

import com.courseregistration.dto.SubjectDTO;
import com.courseregistration.model.Subject;
import com.courseregistration.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class SubjectService {
    
    @Autowired
    private SubjectRepository subjectRepository;
    
    public List<SubjectDTO> getAllSubjects() {
        return subjectRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public Optional<SubjectDTO> getSubjectById(String subjectId) {
        return subjectRepository.findById(subjectId)
                .map(this::convertToDTO);
    }
    
    public List<SubjectDTO> getAvailableSubjects() {
        return subjectRepository.findAvailableSubjects().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<SubjectDTO> getSubjectsWithoutPrerequisites() {
        return subjectRepository.findSubjectsWithoutPrerequisites().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<SubjectDTO> getFacultyCourses() {
        return subjectRepository.findFacultyCourses().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<SubjectDTO> getGeneralEducationCourses() {
        return subjectRepository.findGeneralEducationCourses().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<SubjectDTO> searchSubjectsByName(String name) {
        return subjectRepository.findBySubjectNameContaining(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<SubjectDTO> getSubjectsByInstructor(String instructorName) {
        return subjectRepository.findByInstructorName(instructorName).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<SubjectDTO> getSubjectsByCredits(Integer credits) {
        return subjectRepository.findByCredits(credits).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public SubjectDTO createSubject(SubjectDTO subjectDTO) {
        // Check if subject ID already exists
        if (subjectRepository.existsById(subjectDTO.getSubjectId())) {
            throw new IllegalArgumentException("Subject with ID " + subjectDTO.getSubjectId() + " already exists");
        }
        
        // Validate prerequisite if provided
        if (subjectDTO.hasPrerequisite()) {
            if (!subjectRepository.existsById(subjectDTO.getPrerequisiteSubjectId())) {
                throw new IllegalArgumentException("Prerequisite subject with ID " + subjectDTO.getPrerequisiteSubjectId() + " does not exist");
            }
        }
        
        Subject subject = convertToEntity(subjectDTO);
        Subject savedSubject = subjectRepository.save(subject);
        return convertToDTO(savedSubject);
    }
    
    public SubjectDTO updateSubject(String subjectId, SubjectDTO subjectDTO) {
        Subject existingSubject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found with ID: " + subjectId));
        
        // Validate prerequisite if provided
        if (subjectDTO.hasPrerequisite()) {
            if (!subjectRepository.existsById(subjectDTO.getPrerequisiteSubjectId())) {
                throw new IllegalArgumentException("Prerequisite subject with ID " + subjectDTO.getPrerequisiteSubjectId() + " does not exist");
            }
        }
        
        // Update fields
        existingSubject.setSubjectName(subjectDTO.getSubjectName());
        existingSubject.setCredits(subjectDTO.getCredits());
        existingSubject.setInstructorName(subjectDTO.getInstructorName());
        existingSubject.setPrerequisiteSubjectId(subjectDTO.getPrerequisiteSubjectId());
        existingSubject.setMaxCapacity(subjectDTO.getMaxCapacity());
        
        Subject updatedSubject = subjectRepository.save(existingSubject);
        return convertToDTO(updatedSubject);
    }
    
    public void deleteSubject(String subjectId) {
        if (!subjectRepository.existsById(subjectId)) {
            throw new IllegalArgumentException("Subject not found with ID: " + subjectId);
        }
        subjectRepository.deleteById(subjectId);
    }
    
    public void updateEnrollmentCount(String subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found with ID: " + subjectId));
        
        // This method will be implemented in the RegistrationService
        // For now, we'll keep the current enrollment as is
        subjectRepository.save(subject);
    }
    
    public boolean isSubjectAvailable(String subjectId) {
        Optional<Subject> subject = subjectRepository.findById(subjectId);
        return subject.isPresent() && subject.get().isAvailable();
    }
    
    public boolean hasPrerequisite(String subjectId) {
        Optional<Subject> subject = subjectRepository.findById(subjectId);
        return subject.isPresent() && subject.get().hasPrerequisite();
    }
    
    public String getPrerequisiteSubjectId(String subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found with ID: " + subjectId));
        return subject.getPrerequisiteSubjectId();
    }
    
    // Helper methods
    private SubjectDTO convertToDTO(Subject subject) {
        SubjectDTO dto = new SubjectDTO(
                subject.getSubjectId(),
                subject.getSubjectName(),
                subject.getCredits(),
                subject.getInstructorName(),
                subject.getPrerequisiteSubjectId(),
                subject.getMaxCapacity()
        );
        dto.setCurrentEnrollment(subject.getCurrentEnrollment());
        return dto;
    }
    
    private Subject convertToEntity(SubjectDTO subjectDTO) {
        return new Subject(
                subjectDTO.getSubjectId(),
                subjectDTO.getSubjectName(),
                subjectDTO.getCredits(),
                subjectDTO.getInstructorName(),
                subjectDTO.getPrerequisiteSubjectId(),
                subjectDTO.getMaxCapacity()
        );
    }
}
