package com.courseregistration.controller;

import com.courseregistration.dto.ApiResponse;
import com.courseregistration.dto.RegistrationDTO;
import com.courseregistration.dto.RegistrationRequestDTO;
import com.courseregistration.service.RegistrationService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registrations")
@CrossOrigin(origins = "*")
public class RegistrationController {
    
    @Autowired
    private RegistrationService registrationService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<RegistrationDTO>>> getAllRegistrations() {
        try {
            List<RegistrationDTO> registrations = registrationService.getAllRegistrations();
            return ResponseEntity.ok(ApiResponse.success("Registrations retrieved successfully", registrations));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error retrieving registrations: " + e.getMessage()));
        }
    }
    
    @GetMapping("/student/{studentId}")
    public ResponseEntity<ApiResponse<List<RegistrationDTO>>> getRegistrationsByStudent(@PathVariable String studentId) {
        try {
            List<RegistrationDTO> registrations = registrationService.getRegistrationsByStudent(studentId);
            return ResponseEntity.ok(ApiResponse.success("Student registrations retrieved successfully", registrations));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error retrieving student registrations: " + e.getMessage()));
        }
    }
    
    @GetMapping("/student/{studentId}/active")
    public ResponseEntity<ApiResponse<List<RegistrationDTO>>> getActiveRegistrationsByStudent(@PathVariable String studentId) {
        try {
            List<RegistrationDTO> registrations = registrationService.getActiveRegistrationsByStudent(studentId);
            return ResponseEntity.ok(ApiResponse.success("Active student registrations retrieved successfully", registrations));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error retrieving active student registrations: " + e.getMessage()));
        }
    }
    
    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<ApiResponse<List<RegistrationDTO>>> getRegistrationsBySubject(@PathVariable String subjectId) {
        try {
            List<RegistrationDTO> registrations = registrationService.getRegistrationsBySubject(subjectId);
            return ResponseEntity.ok(ApiResponse.success("Subject registrations retrieved successfully", registrations));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error retrieving subject registrations: " + e.getMessage()));
        }
    }
    
    @GetMapping("/subject/{subjectId}/active")
    public ResponseEntity<ApiResponse<List<RegistrationDTO>>> getActiveRegistrationsBySubject(@PathVariable String subjectId) {
        try {
            List<RegistrationDTO> registrations = registrationService.getActiveRegistrationsBySubject(subjectId);
            return ResponseEntity.ok(ApiResponse.success("Active subject registrations retrieved successfully", registrations));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error retrieving active subject registrations: " + e.getMessage()));
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegistrationDTO>> registerStudent(@Valid @RequestBody RegistrationRequestDTO request) {
        try {
            RegistrationDTO registration = registrationService.registerStudent(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Student registered successfully", registration));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error registering student: " + e.getMessage()));
        }
    }
    
    @PutMapping("/{registrationId}/cancel")
    public ResponseEntity<ApiResponse<RegistrationDTO>> cancelRegistration(@PathVariable Long registrationId) {
        try {
            RegistrationDTO registration = registrationService.cancelRegistration(registrationId);
            return ResponseEntity.ok(ApiResponse.success("Registration cancelled successfully", registration));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error cancelling registration: " + e.getMessage()));
        }
    }
    
    @PutMapping("/{registrationId}/complete")
    public ResponseEntity<ApiResponse<RegistrationDTO>> completeRegistration(@PathVariable Long registrationId) {
        try {
            RegistrationDTO registration = registrationService.completeRegistration(registrationId);
            return ResponseEntity.ok(ApiResponse.success("Registration completed successfully", registration));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error completing registration: " + e.getMessage()));
        }
    }
    
    @GetMapping("/student/{studentId}/available")
    public ResponseEntity<ApiResponse<List<RegistrationDTO>>> getAvailableSubjectsForStudent(@PathVariable String studentId) {
        try {
            List<RegistrationDTO> availableSubjects = registrationService.getAvailableSubjectsForStudent(studentId);
            return ResponseEntity.ok(ApiResponse.success("Available subjects retrieved successfully", availableSubjects));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error retrieving available subjects: " + e.getMessage()));
        }
    }
}
