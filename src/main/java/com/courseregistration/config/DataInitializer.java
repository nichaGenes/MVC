package com.courseregistration.config;

import com.courseregistration.model.Student;
import com.courseregistration.model.Subject;
import com.courseregistration.repository.StudentRepository;
import com.courseregistration.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private SubjectRepository subjectRepository;
    
    @Override
    public void run(String... args) throws Exception {
        initializeStudents();
        initializeSubjects();
    }
    
    private void initializeStudents() {
        if (studentRepository.count() == 0) {
            // Sample students
            Student student1 = new Student("69000001", "Mr.", "John", "Doe", 
                    LocalDate.of(2005, 3, 15), "Springfield High School", "john.doe@email.com");
            
            Student student2 = new Student("69000002", "Ms.", "Jane", "Smith", 
                    LocalDate.of(2006, 7, 22), "Springfield High School", "jane.smith@email.com");
            
            Student student3 = new Student("69000003", "Mr.", "Mike", "Johnson", 
                    LocalDate.of(2005, 11, 8), "Lincoln High School", "mike.johnson@email.com");
            
            Student student4 = new Student("69000004", "Ms.", "Sarah", "Wilson", 
                    LocalDate.of(2006, 1, 30), "Springfield High School", "sarah.wilson@email.com");
            
            Student student5 = new Student("69000005", "Mr.", "David", "Brown", 
                    LocalDate.of(2005, 9, 12), "Washington High School", "david.brown@email.com");
            
            studentRepository.save(student1);
            studentRepository.save(student2);
            studentRepository.save(student3);
            studentRepository.save(student4);
            studentRepository.save(student5);
            
            System.out.println("Sample students initialized successfully!");
        }
    }
    
    private void initializeSubjects() {
        if (subjectRepository.count() == 0) {
            // Faculty Courses (0550xxxx)
            Subject math101 = new Subject("05500001", "Advanced Mathematics", 3, 
                    "Dr. Alice Johnson", null, 30);
            
            Subject physics101 = new Subject("05500002", "Physics Fundamentals", 3, 
                    "Prof. Robert Smith", "05500001", 25);
            
            Subject chemistry101 = new Subject("05500003", "General Chemistry", 3, 
                    "Dr. Emily Davis", "05500001", 20);
            
            Subject biology101 = new Subject("05500004", "Biology Basics", 3, 
                    "Prof. Michael Wilson", null, 25);
            
            Subject computer101 = new Subject("05500005", "Introduction to Programming", 3, 
                    "Dr. Sarah Brown", null, 30);
            
            // General Education Courses (9069xxxx)
            Subject english101 = new Subject("90690001", "English Literature", 2, 
                    "Ms. Lisa Anderson", null, 35);
            
            Subject history101 = new Subject("90690002", "World History", 2, 
                    "Mr. James Taylor", null, 30);
            
            Subject art101 = new Subject("90690003", "Art and Design", 2, 
                    "Ms. Maria Garcia", null, 20);
            
            Subject music101 = new Subject("90690004", "Music Appreciation", 2, 
                    "Mr. Kevin Lee", null, 25);
            
            Subject pe101 = new Subject("90690005", "Physical Education", 1, 
                    "Coach Tom Wilson", null, 40);
            
            Subject psychology101 = new Subject("90690006", "Introduction to Psychology", 2, 
                    "Dr. Jennifer White", null, 30);
            
            Subject economics101 = new Subject("90690007", "Basic Economics", 2, 
                    "Prof. Mark Thompson", null, 25);
            
            Subject geography101 = new Subject("90690008", "Geography", 2, 
                    "Ms. Rachel Green", null, 30);
            
            subjectRepository.save(math101);
            subjectRepository.save(physics101);
            subjectRepository.save(chemistry101);
            subjectRepository.save(biology101);
            subjectRepository.save(computer101);
            subjectRepository.save(english101);
            subjectRepository.save(history101);
            subjectRepository.save(art101);
            subjectRepository.save(music101);
            subjectRepository.save(pe101);
            subjectRepository.save(psychology101);
            subjectRepository.save(economics101);
            subjectRepository.save(geography101);
            
            System.out.println("Sample subjects initialized successfully!");
        }
    }
}
