package com.courseregistration.service;

import com.courseregistration.dto.StudentDTO;
import com.courseregistration.model.Student;
import com.courseregistration.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public Optional<StudentDTO> getStudentById(String studentId) {
        return studentRepository.findById(studentId)
                .map(this::convertToDTO);
    }
    
    public Optional<StudentDTO> getStudentByEmail(String email) {
        return studentRepository.findByEmail(email)
                .map(this::convertToDTO);
    }
    
    public List<StudentDTO> getStudentsBySchool(String school) {
        return studentRepository.findByCurrentSchool(school).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<StudentDTO> searchStudentsByName(String name) {
        return studentRepository.findByNameContaining(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public StudentDTO createStudent(StudentDTO studentDTO) {
        // Check if student ID already exists
        if (studentRepository.existsById(studentDTO.getStudentId())) {
            throw new IllegalArgumentException("Student with ID " + studentDTO.getStudentId() + " already exists");
        }
        
        // Check if email already exists
        if (studentRepository.existsByEmail(studentDTO.getEmail())) {
            throw new IllegalArgumentException("Student with email " + studentDTO.getEmail() + " already exists");
        }
        
        // Validate age (must be at least 15)
        if (studentDTO.getAge() < 15) {
            throw new IllegalArgumentException("Student must be at least 15 years old to register");
        }
        
        Student student = convertToEntity(studentDTO);
        Student savedStudent = studentRepository.save(student);
        return convertToDTO(savedStudent);
    }
    
    public StudentDTO updateStudent(String studentId, StudentDTO studentDTO) {
        Student existingStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));
        
        // Check if email is being changed and if it already exists
        if (!existingStudent.getEmail().equals(studentDTO.getEmail()) && 
            studentRepository.existsByEmail(studentDTO.getEmail())) {
            throw new IllegalArgumentException("Student with email " + studentDTO.getEmail() + " already exists");
        }
        
        // Validate age (must be at least 15)
        if (studentDTO.getAge() < 15) {
            throw new IllegalArgumentException("Student must be at least 15 years old to register");
        }
        
        // Update fields
        existingStudent.setTitle(studentDTO.getTitle());
        existingStudent.setFirstName(studentDTO.getFirstName());
        existingStudent.setLastName(studentDTO.getLastName());
        existingStudent.setDateOfBirth(studentDTO.getDateOfBirth());
        existingStudent.setCurrentSchool(studentDTO.getCurrentSchool());
        existingStudent.setEmail(studentDTO.getEmail());
        
        Student updatedStudent = studentRepository.save(existingStudent);
        return convertToDTO(updatedStudent);
    }
    
    public void deleteStudent(String studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new IllegalArgumentException("Student not found with ID: " + studentId);
        }
        studentRepository.deleteById(studentId);
    }
    
    public boolean isStudentEligible(String studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        return student.isPresent() && student.get().getAge() >= 15;
    }
    
    // Helper methods
    private StudentDTO convertToDTO(Student student) {
        return new StudentDTO(
                student.getStudentId(),
                student.getTitle(),
                student.getFirstName(),
                student.getLastName(),
                student.getDateOfBirth(),
                student.getCurrentSchool(),
                student.getEmail()
        );
    }
    
    private Student convertToEntity(StudentDTO studentDTO) {
        return new Student(
                studentDTO.getStudentId(),
                studentDTO.getTitle(),
                studentDTO.getFirstName(),
                studentDTO.getLastName(),
                studentDTO.getDateOfBirth(),
                studentDTO.getCurrentSchool(),
                studentDTO.getEmail()
        );
    }
}
