package com.courseregistration.controller;

import com.courseregistration.dto.ApiResponse;
import com.courseregistration.dto.StudentDTO;
import com.courseregistration.service.StudentService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentDTO>>> getAllStudents() {
        try {
            List<StudentDTO> students = studentService.getAllStudents();
            return ResponseEntity.ok(ApiResponse.success("Students retrieved successfully", students));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error retrieving students: " + e.getMessage()));
        }
    }
    
    @GetMapping("/{studentId}")
    public ResponseEntity<ApiResponse<StudentDTO>> getStudentById(@PathVariable String studentId) {
        try {
            Optional<StudentDTO> student = studentService.getStudentById(studentId);
            if (student.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success("Student retrieved successfully", student.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Student not found with ID: " + studentId));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error retrieving student: " + e.getMessage()));
        }
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<StudentDTO>> getStudentByEmail(@PathVariable String email) {
        try {
            Optional<StudentDTO> student = studentService.getStudentByEmail(email);
            if (student.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success("Student retrieved successfully", student.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Student not found with email: " + email));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error retrieving student: " + e.getMessage()));
        }
    }
    
    @GetMapping("/school/{school}")
    public ResponseEntity<ApiResponse<List<StudentDTO>>> getStudentsBySchool(@PathVariable String school) {
        try {
            List<StudentDTO> students = studentService.getStudentsBySchool(school);
            return ResponseEntity.ok(ApiResponse.success("Students retrieved successfully", students));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error retrieving students: " + e.getMessage()));
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<StudentDTO>>> searchStudentsByName(@RequestParam String name) {
        try {
            List<StudentDTO> students = studentService.searchStudentsByName(name);
            return ResponseEntity.ok(ApiResponse.success("Students retrieved successfully", students));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error searching students: " + e.getMessage()));
        }
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<StudentDTO>> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        try {
            StudentDTO createdStudent = studentService.createStudent(studentDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Student created successfully", createdStudent));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error creating student: " + e.getMessage()));
        }
    }
    
    @PutMapping("/{studentId}")
    public ResponseEntity<ApiResponse<StudentDTO>> updateStudent(@PathVariable String studentId, 
                                                                @Valid @RequestBody StudentDTO studentDTO) {
        try {
            StudentDTO updatedStudent = studentService.updateStudent(studentId, studentDTO);
            return ResponseEntity.ok(ApiResponse.success("Student updated successfully", updatedStudent));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error updating student: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/{studentId}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable String studentId) {
        try {
            studentService.deleteStudent(studentId);
            return ResponseEntity.ok(ApiResponse.success("Student deleted successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error deleting student: " + e.getMessage()));
        }
    }
    
    @GetMapping("/{studentId}/eligible")
    public ResponseEntity<ApiResponse<Boolean>> isStudentEligible(@PathVariable String studentId) {
        try {
            boolean eligible = studentService.isStudentEligible(studentId);
            return ResponseEntity.ok(ApiResponse.success("Eligibility checked successfully", eligible));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error checking eligibility: " + e.getMessage()));
        }
    }
}
