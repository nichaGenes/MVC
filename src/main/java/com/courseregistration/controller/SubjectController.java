package com.courseregistration.controller;

import com.courseregistration.dto.ApiResponse;
import com.courseregistration.dto.SubjectDTO;
import com.courseregistration.service.SubjectService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/subjects")
@CrossOrigin(origins = "*")
public class SubjectController {
    
    @Autowired
    private SubjectService subjectService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<SubjectDTO>>> getAllSubjects() {
        try {
            List<SubjectDTO> subjects = subjectService.getAllSubjects();
            return ResponseEntity.ok(ApiResponse.success("Subjects retrieved successfully", subjects));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error retrieving subjects: " + e.getMessage()));
        }
    }
    
    @GetMapping("/{subjectId}")
    public ResponseEntity<ApiResponse<SubjectDTO>> getSubjectById(@PathVariable String subjectId) {
        try {
            Optional<SubjectDTO> subject = subjectService.getSubjectById(subjectId);
            if (subject.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success("Subject retrieved successfully", subject.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Subject not found with ID: " + subjectId));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error retrieving subject: " + e.getMessage()));
        }
    }
    
    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<SubjectDTO>>> getAvailableSubjects() {
        try {
            List<SubjectDTO> subjects = subjectService.getAvailableSubjects();
            return ResponseEntity.ok(ApiResponse.success("Available subjects retrieved successfully", subjects));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error retrieving available subjects: " + e.getMessage()));
        }
    }
    
    @GetMapping("/no-prerequisites")
    public ResponseEntity<ApiResponse<List<SubjectDTO>>> getSubjectsWithoutPrerequisites() {
        try {
            List<SubjectDTO> subjects = subjectService.getSubjectsWithoutPrerequisites();
            return ResponseEntity.ok(ApiResponse.success("Subjects without prerequisites retrieved successfully", subjects));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error retrieving subjects without prerequisites: " + e.getMessage()));
        }
    }
    
    @GetMapping("/faculty")
    public ResponseEntity<ApiResponse<List<SubjectDTO>>> getFacultyCourses() {
        try {
            List<SubjectDTO> subjects = subjectService.getFacultyCourses();
            return ResponseEntity.ok(ApiResponse.success("Faculty courses retrieved successfully", subjects));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error retrieving faculty courses: " + e.getMessage()));
        }
    }
    
    @GetMapping("/general-education")
    public ResponseEntity<ApiResponse<List<SubjectDTO>>> getGeneralEducationCourses() {
        try {
            List<SubjectDTO> subjects = subjectService.getGeneralEducationCourses();
            return ResponseEntity.ok(ApiResponse.success("General education courses retrieved successfully", subjects));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error retrieving general education courses: " + e.getMessage()));
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<SubjectDTO>>> searchSubjectsByName(@RequestParam String name) {
        try {
            List<SubjectDTO> subjects = subjectService.searchSubjectsByName(name);
            return ResponseEntity.ok(ApiResponse.success("Subjects retrieved successfully", subjects));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error searching subjects: " + e.getMessage()));
        }
    }
    
    @GetMapping("/instructor/{instructorName}")
    public ResponseEntity<ApiResponse<List<SubjectDTO>>> getSubjectsByInstructor(@PathVariable String instructorName) {
        try {
            List<SubjectDTO> subjects = subjectService.getSubjectsByInstructor(instructorName);
            return ResponseEntity.ok(ApiResponse.success("Subjects retrieved successfully", subjects));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error retrieving subjects: " + e.getMessage()));
        }
    }
    
    @GetMapping("/credits/{credits}")
    public ResponseEntity<ApiResponse<List<SubjectDTO>>> getSubjectsByCredits(@PathVariable Integer credits) {
        try {
            List<SubjectDTO> subjects = subjectService.getSubjectsByCredits(credits);
            return ResponseEntity.ok(ApiResponse.success("Subjects retrieved successfully", subjects));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error retrieving subjects: " + e.getMessage()));
        }
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<SubjectDTO>> createSubject(@Valid @RequestBody SubjectDTO subjectDTO) {
        try {
            SubjectDTO createdSubject = subjectService.createSubject(subjectDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Subject created successfully", createdSubject));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error creating subject: " + e.getMessage()));
        }
    }
    
    @PutMapping("/{subjectId}")
    public ResponseEntity<ApiResponse<SubjectDTO>> updateSubject(@PathVariable String subjectId, 
                                                               @Valid @RequestBody SubjectDTO subjectDTO) {
        try {
            SubjectDTO updatedSubject = subjectService.updateSubject(subjectId, subjectDTO);
            return ResponseEntity.ok(ApiResponse.success("Subject updated successfully", updatedSubject));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error updating subject: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/{subjectId}")
    public ResponseEntity<ApiResponse<Void>> deleteSubject(@PathVariable String subjectId) {
        try {
            subjectService.deleteSubject(subjectId);
            return ResponseEntity.ok(ApiResponse.success("Subject deleted successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error deleting subject: " + e.getMessage()));
        }
    }
    
    @GetMapping("/{subjectId}/available")
    public ResponseEntity<ApiResponse<Boolean>> isSubjectAvailable(@PathVariable String subjectId) {
        try {
            boolean available = subjectService.isSubjectAvailable(subjectId);
            return ResponseEntity.ok(ApiResponse.success("Availability checked successfully", available));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error checking availability: " + e.getMessage()));
        }
    }
}
