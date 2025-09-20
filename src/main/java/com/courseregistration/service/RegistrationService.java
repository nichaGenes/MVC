package com.courseregistration.service;

import com.courseregistration.dto.RegistrationDTO;
import com.courseregistration.dto.RegistrationRequestDTO;
import com.courseregistration.model.Registration;
import com.courseregistration.model.RegistrationStatus;
import com.courseregistration.repository.RegistrationRepository;
import com.courseregistration.repository.StudentRepository;
import com.courseregistration.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RegistrationService {
    
    @Autowired
    private RegistrationRepository registrationRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private SubjectRepository subjectRepository;
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private SubjectService subjectService;
    
    public List<RegistrationDTO> getAllRegistrations() {
        return registrationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<RegistrationDTO> getRegistrationsByStudent(String studentId) {
        return registrationRepository.findByStudentId(studentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<RegistrationDTO> getActiveRegistrationsByStudent(String studentId) {
        return registrationRepository.findActiveRegistrationsByStudentId(studentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<RegistrationDTO> getRegistrationsBySubject(String subjectId) {
        return registrationRepository.findBySubjectId(subjectId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<RegistrationDTO> getActiveRegistrationsBySubject(String subjectId) {
        return registrationRepository.findActiveRegistrationsBySubjectId(subjectId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public RegistrationDTO registerStudent(RegistrationRequestDTO request) {
        String studentId = request.getStudentId();
        String subjectId = request.getSubjectId();
        
        // Validate student exists and is eligible
        if (!studentService.isStudentEligible(studentId)) {
            throw new IllegalArgumentException("Student is not eligible for registration (must be at least 15 years old)");
        }
        
        // Validate subject exists and is available
        if (!subjectService.isSubjectAvailable(subjectId)) {
            throw new IllegalArgumentException("Subject is not available for registration");
        }
        
        // Check if student is already registered for this subject
        if (registrationRepository.existsByStudentIdAndSubjectIdAndStatus(studentId, subjectId, RegistrationStatus.ACTIVE)) {
            throw new IllegalArgumentException("Student is already registered for this subject");
        }
        
        // Check prerequisites
        if (subjectService.hasPrerequisite(subjectId)) {
            String prerequisiteId = subjectService.getPrerequisiteSubjectId(subjectId);
            if (!hasCompletedPrerequisite(studentId, prerequisiteId)) {
                throw new IllegalArgumentException("Student has not completed the prerequisite course: " + prerequisiteId);
            }
        }
        
        // Create registration
        Registration registration = new Registration(studentId, subjectId);
        Registration savedRegistration = registrationRepository.save(registration);
        
        // Update subject enrollment count
        subjectService.updateEnrollmentCount(subjectId);
        
        return convertToDTO(savedRegistration);
    }
    
    public RegistrationDTO cancelRegistration(Long registrationId) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new IllegalArgumentException("Registration not found with ID: " + registrationId));
        
        if (registration.getStatus() != RegistrationStatus.ACTIVE) {
            throw new IllegalArgumentException("Registration is not active and cannot be cancelled");
        }
        
        registration.setStatus(RegistrationStatus.CANCELLED);
        Registration updatedRegistration = registrationRepository.save(registration);
        
        // Update subject enrollment count
        subjectService.updateEnrollmentCount(registration.getSubjectId());
        
        return convertToDTO(updatedRegistration);
    }
    
    public RegistrationDTO completeRegistration(Long registrationId) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new IllegalArgumentException("Registration not found with ID: " + registrationId));
        
        if (registration.getStatus() != RegistrationStatus.ACTIVE) {
            throw new IllegalArgumentException("Registration is not active and cannot be completed");
        }
        
        registration.setStatus(RegistrationStatus.COMPLETED);
        Registration updatedRegistration = registrationRepository.save(registration);
        
        return convertToDTO(updatedRegistration);
    }
    
    public List<RegistrationDTO> getAvailableSubjectsForStudent(String studentId) {
        // Get all subjects
        List<String> allSubjectIds = subjectRepository.findAll().stream()
                .map(subject -> subject.getSubjectId())
                .collect(Collectors.toList());
        
        // Get registered subject IDs for this student
        List<String> registeredSubjectIds = registrationRepository.findActiveRegistrationsByStudentId(studentId).stream()
                .map(registration -> registration.getSubjectId())
                .collect(Collectors.toList());
        
        // Filter out already registered subjects
        List<String> availableSubjectIds = allSubjectIds.stream()
                .filter(subjectId -> !registeredSubjectIds.contains(subjectId))
                .collect(Collectors.toList());
        
        // Convert to DTOs
        return availableSubjectIds.stream()
                .map(subjectId -> {
                    RegistrationDTO dto = new RegistrationDTO();
                    dto.setSubjectId(subjectId);
                    // You can add more details here if needed
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    private boolean hasCompletedPrerequisite(String studentId, String prerequisiteId) {
        return registrationRepository.findByStudentIdAndSubjectId(studentId, prerequisiteId).stream()
                .anyMatch(registration -> registration.getStatus() == RegistrationStatus.COMPLETED);
    }
    
    // Helper methods
    private RegistrationDTO convertToDTO(Registration registration) {
        RegistrationDTO dto = new RegistrationDTO(
                registration.getStudentId(),
                registration.getSubjectId()
        );
        dto.setId(registration.getId());
        dto.setRegistrationDate(registration.getRegistrationDate());
        dto.setStatus(registration.getStatus());
        
        // Add student and subject names if entities are loaded
        if (registration.getStudent() != null) {
            dto.setStudentName(registration.getStudent().getFullName());
        }
        if (registration.getSubject() != null) {
            dto.setSubjectName(registration.getSubject().getSubjectName());
        }
        
        return dto;
    }
}
