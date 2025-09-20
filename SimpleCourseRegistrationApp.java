import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple Course Registration System - Standalone Java Application
 * This version runs without Spring Boot or Maven dependencies
 */
public class SimpleCourseRegistrationApp {
    
    // Data storage using ConcurrentHashMap for thread safety
    private static Map<String, Student> students = new ConcurrentHashMap<>();
    private static Map<String, Subject> subjects = new ConcurrentHashMap<>();
    private static Map<Long, Registration> registrations = new ConcurrentHashMap<>();
    private static long registrationIdCounter = 1;
    
    public static void main(String[] args) {
        System.out.println("🎓 Welcome to Course Registration System! 🎓");
        System.out.println("================================================");
        
        // Initialize sample data
        initializeSampleData();
        
        // Display menu and handle user input
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        while (running) {
            displayMenu();
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    displayAllStudents();
                    break;
                case "2":
                    displayAllSubjects();
                    break;
                case "3":
                    registerStudent(scanner);
                    break;
                case "4":
                    displayStudentRegistrations(scanner);
                    break;
                case "5":
                    displaySubjectDetails(scanner);
                    break;
                case "6":
                    addNewStudent(scanner);
                    break;
                case "7":
                    addNewSubject(scanner);
                    break;
                case "8":
                    displayAvailableSubjects(scanner);
                    break;
                case "9":
                    cancelRegistration(scanner);
                    break;
                case "0":
                    System.out.println("Thank you for using Course Registration System! 👋");
                    running = false;
                    break;
                default:
                    System.out.println("❌ Invalid choice. Please try again.");
            }
            
            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    private static void displayMenu() {
        System.out.println("\n📋 MAIN MENU");
        System.out.println("=============");
        System.out.println("1. 📚 View All Students");
        System.out.println("2. 📖 View All Subjects");
        System.out.println("3. ✍️  Register Student for Course");
        System.out.println("4. 👤 View Student Registrations");
        System.out.println("5. 📄 View Subject Details");
        System.out.println("6. ➕ Add New Student");
        System.out.println("7. ➕ Add New Subject");
        System.out.println("8. 🔍 View Available Subjects for Student");
        System.out.println("9. ❌ Cancel Registration");
        System.out.println("0. 🚪 Exit");
        System.out.println("=============");
    }
    
    private static void initializeSampleData() {
        System.out.println("📊 Initializing sample data...");
        
        // Sample Students
        students.put("69000001", new Student("69000001", "Mr.", "John", "Doe", 
                LocalDate.of(2005, 3, 15), "Springfield High School", "john.doe@email.com"));
        students.put("69000002", new Student("69000002", "Ms.", "Jane", "Smith", 
                LocalDate.of(2006, 7, 22), "Springfield High School", "jane.smith@email.com"));
        students.put("69000003", new Student("69000003", "Mr.", "Mike", "Johnson", 
                LocalDate.of(2005, 11, 8), "Lincoln High School", "mike.johnson@email.com"));
        students.put("69000004", new Student("69000004", "Ms.", "Sarah", "Wilson", 
                LocalDate.of(2006, 1, 30), "Springfield High School", "sarah.wilson@email.com"));
        students.put("69000005", new Student("69000005", "Mr.", "David", "Brown", 
                LocalDate.of(2005, 9, 12), "Washington High School", "david.brown@email.com"));
        
        // Sample Subjects
        subjects.put("05500001", new Subject("05500001", "Advanced Mathematics", 3, 
                "Dr. Alice Johnson", null, 30));
        subjects.put("05500002", new Subject("05500002", "Physics Fundamentals", 3, 
                "Prof. Robert Smith", "05500001", 25));
        subjects.put("05500003", new Subject("05500003", "General Chemistry", 3, 
                "Dr. Emily Davis", "05500001", 20));
        subjects.put("05500004", new Subject("05500004", "Biology Basics", 3, 
                "Prof. Michael Wilson", null, 25));
        subjects.put("05500005", new Subject("05500005", "Introduction to Programming", 3, 
                "Dr. Sarah Brown", null, 30));
        
        subjects.put("90690001", new Subject("90690001", "English Literature", 2, 
                "Ms. Lisa Anderson", null, 35));
        subjects.put("90690002", new Subject("90690002", "World History", 2, 
                "Mr. James Taylor", null, 30));
        subjects.put("90690003", new Subject("90690003", "Art and Design", 2, 
                "Ms. Maria Garcia", null, 20));
        subjects.put("90690004", new Subject("90690004", "Music Appreciation", 2, 
                "Mr. Kevin Lee", null, 25));
        subjects.put("90690005", new Subject("90690005", "Physical Education", 1, 
                "Coach Tom Wilson", null, 40));
        
        System.out.println("✅ Sample data initialized successfully!");
        System.out.println("   - 5 Students");
        System.out.println("   - 10 Subjects (5 Faculty + 5 General Education)");
    }
    
    private static void displayAllStudents() {
        System.out.println("\n👥 ALL STUDENTS");
        System.out.println("===============");
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        
        for (Student student : students.values()) {
            System.out.println("ID: " + student.studentId);
            System.out.println("Name: " + student.getFullName());
            System.out.println("Age: " + student.getAge() + " years");
            System.out.println("School: " + student.currentSchool);
            System.out.println("Email: " + student.email);
            System.out.println("Eligible: " + (student.getAge() >= 15 ? "✅ Yes" : "❌ No (under 15)"));
            System.out.println("---");
        }
    }
    
    private static void displayAllSubjects() {
        System.out.println("\n📚 ALL SUBJECTS");
        System.out.println("===============");
        if (subjects.isEmpty()) {
            System.out.println("No subjects found.");
            return;
        }
        
        for (Subject subject : subjects.values()) {
            System.out.println("ID: " + subject.subjectId);
            System.out.println("Name: " + subject.subjectName);
            System.out.println("Type: " + subject.getCourseType());
            System.out.println("Credits: " + subject.credits);
            System.out.println("Instructor: " + subject.instructorName);
            System.out.println("Capacity: " + subject.currentEnrollment + "/" + 
                    (subject.maxCapacity == -1 ? "∞" : subject.maxCapacity));
            System.out.println("Available: " + (subject.isAvailable() ? "✅ Yes" : "❌ No"));
            if (subject.hasPrerequisite()) {
                System.out.println("Prerequisite: " + subject.prerequisiteSubjectId);
            }
            System.out.println("---");
        }
    }
    
    private static void registerStudent(Scanner scanner) {
        System.out.println("\n✍️  REGISTER STUDENT FOR COURSE");
        System.out.println("================================");
        
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine().trim();
        
        if (!students.containsKey(studentId)) {
            System.out.println("❌ Student not found with ID: " + studentId);
            return;
        }
        
        Student student = students.get(studentId);
        if (student.getAge() < 15) {
            System.out.println("❌ Student must be at least 15 years old to register.");
            return;
        }
        
        System.out.print("Enter Subject ID: ");
        String subjectId = scanner.nextLine().trim();
        
        if (!subjects.containsKey(subjectId)) {
            System.out.println("❌ Subject not found with ID: " + subjectId);
            return;
        }
        
        Subject subject = subjects.get(subjectId);
        
        // Check if already registered
        for (Registration reg : registrations.values()) {
            if (reg.studentId.equals(studentId) && reg.subjectId.equals(subjectId) && 
                reg.status == RegistrationStatus.ACTIVE) {
                System.out.println("❌ Student is already registered for this subject.");
                return;
            }
        }
        
        // Check prerequisites
        if (subject.hasPrerequisite()) {
            boolean hasPrerequisite = false;
            for (Registration reg : registrations.values()) {
                if (reg.studentId.equals(studentId) && 
                    reg.subjectId.equals(subject.prerequisiteSubjectId) && 
                    reg.status == RegistrationStatus.COMPLETED) {
                    hasPrerequisite = true;
                    break;
                }
            }
            if (!hasPrerequisite) {
                System.out.println("❌ Student has not completed prerequisite: " + subject.prerequisiteSubjectId);
                return;
            }
        }
        
        // Check capacity
        if (!subject.isAvailable()) {
            System.out.println("❌ Subject is at full capacity.");
            return;
        }
        
        // Register student
        Registration registration = new Registration(studentId, subjectId);
        registrations.put(registration.id, registration);
        subject.currentEnrollment++;
        
        System.out.println("✅ Registration successful!");
        System.out.println("Registration ID: " + registration.id);
        System.out.println("Student: " + student.getFullName());
        System.out.println("Subject: " + subject.subjectName);
        System.out.println("Date: " + registration.registrationDate);
    }
    
    private static void displayStudentRegistrations(Scanner scanner) {
        System.out.println("\n👤 STUDENT REGISTRATIONS");
        System.out.println("=========================");
        
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine().trim();
        
        if (!students.containsKey(studentId)) {
            System.out.println("❌ Student not found with ID: " + studentId);
            return;
        }
        
        Student student = students.get(studentId);
        System.out.println("Student: " + student.getFullName());
        System.out.println("Registrations:");
        
        boolean found = false;
        for (Registration reg : registrations.values()) {
            if (reg.studentId.equals(studentId)) {
                Subject subject = subjects.get(reg.subjectId);
                System.out.println("  ID: " + reg.id);
                System.out.println("  Subject: " + subject.subjectName + " (" + reg.subjectId + ")");
                System.out.println("  Status: " + reg.status);
                System.out.println("  Date: " + reg.registrationDate);
                System.out.println("  ---");
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("  No registrations found.");
        }
    }
    
    private static void displaySubjectDetails(Scanner scanner) {
        System.out.println("\n📄 SUBJECT DETAILS");
        System.out.println("==================");
        
        System.out.print("Enter Subject ID: ");
        String subjectId = scanner.nextLine().trim();
        
        if (!subjects.containsKey(subjectId)) {
            System.out.println("❌ Subject not found with ID: " + subjectId);
            return;
        }
        
        Subject subject = subjects.get(subjectId);
        System.out.println("Subject ID: " + subject.subjectId);
        System.out.println("Name: " + subject.subjectName);
        System.out.println("Type: " + subject.getCourseType());
        System.out.println("Credits: " + subject.credits);
        System.out.println("Instructor: " + subject.instructorName);
        System.out.println("Capacity: " + subject.currentEnrollment + "/" + 
                (subject.maxCapacity == -1 ? "∞" : subject.maxCapacity));
        System.out.println("Available: " + (subject.isAvailable() ? "✅ Yes" : "❌ No"));
        
        if (subject.hasPrerequisite()) {
            Subject prereq = subjects.get(subject.prerequisiteSubjectId);
            System.out.println("Prerequisite: " + prereq.subjectName + " (" + subject.prerequisiteSubjectId + ")");
        } else {
            System.out.println("Prerequisite: None");
        }
        
        System.out.println("\nRegistered Students:");
        boolean found = false;
        for (Registration reg : registrations.values()) {
            if (reg.subjectId.equals(subjectId) && reg.status == RegistrationStatus.ACTIVE) {
                Student student = students.get(reg.studentId);
                System.out.println("  " + student.getFullName() + " (" + reg.studentId + ")");
                found = true;
            }
        }
        if (!found) {
            System.out.println("  No active registrations.");
        }
    }
    
    private static void addNewStudent(Scanner scanner) {
        System.out.println("\n➕ ADD NEW STUDENT");
        System.out.println("==================");
        
        System.out.print("Student ID (8 digits, start with 69): ");
        String studentId = scanner.nextLine().trim();
        
        if (!studentId.matches("^69\\d{6}$")) {
            System.out.println("❌ Student ID must be 8 digits starting with '69'");
            return;
        }
        
        if (students.containsKey(studentId)) {
            System.out.println("❌ Student ID already exists");
            return;
        }
        
        System.out.print("Title (Mr./Ms./Dr.): ");
        String title = scanner.nextLine().trim();
        
        System.out.print("First Name: ");
        String firstName = scanner.nextLine().trim();
        
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine().trim();
        
        System.out.print("Date of Birth (YYYY-MM-DD): ");
        String dobStr = scanner.nextLine().trim();
        
        try {
            LocalDate dateOfBirth = LocalDate.parse(dobStr);
            if (dateOfBirth.isAfter(LocalDate.now())) {
                System.out.println("❌ Date of birth cannot be in the future");
                return;
            }
            
            System.out.print("Current School: ");
            String currentSchool = scanner.nextLine().trim();
            
            System.out.print("Email: ");
            String email = scanner.nextLine().trim();
            
            Student newStudent = new Student(studentId, title, firstName, lastName, 
                    dateOfBirth, currentSchool, email);
            students.put(studentId, newStudent);
            
            System.out.println("✅ Student added successfully!");
            System.out.println("Student: " + newStudent.getFullName());
            System.out.println("Age: " + newStudent.getAge() + " years");
            System.out.println("Eligible: " + (newStudent.getAge() >= 15 ? "✅ Yes" : "❌ No (under 15)"));
            
        } catch (Exception e) {
            System.out.println("❌ Invalid date format. Please use YYYY-MM-DD");
        }
    }
    
    private static void addNewSubject(Scanner scanner) {
        System.out.println("\n➕ ADD NEW SUBJECT");
        System.out.println("==================");
        
        System.out.print("Subject ID (8 digits, start with 0550 or 9069): ");
        String subjectId = scanner.nextLine().trim();
        
        if (!subjectId.matches("^(0550|9069)\\d{4}$")) {
            System.out.println("❌ Subject ID must be 8 digits starting with '0550' or '9069'");
            return;
        }
        
        if (subjects.containsKey(subjectId)) {
            System.out.println("❌ Subject ID already exists");
            return;
        }
        
        System.out.print("Subject Name: ");
        String subjectName = scanner.nextLine().trim();
        
        System.out.print("Credits: ");
        String creditsStr = scanner.nextLine().trim();
        
        try {
            int credits = Integer.parseInt(creditsStr);
            if (credits <= 0) {
                System.out.println("❌ Credits must be greater than 0");
                return;
            }
            
            System.out.print("Instructor Name: ");
            String instructorName = scanner.nextLine().trim();
            
            System.out.print("Prerequisite Subject ID (optional, press Enter to skip): ");
            String prerequisiteId = scanner.nextLine().trim();
            
            if (!prerequisiteId.isEmpty() && !subjects.containsKey(prerequisiteId)) {
                System.out.println("❌ Prerequisite subject not found");
                return;
            }
            
            System.out.print("Max Capacity (-1 for unlimited): ");
            String capacityStr = scanner.nextLine().trim();
            
            int maxCapacity = Integer.parseInt(capacityStr);
            if (maxCapacity < -1 || maxCapacity == 0) {
                System.out.println("❌ Max capacity must be -1 for unlimited or a positive number");
                return;
            }
            
            Subject newSubject = new Subject(subjectId, subjectName, credits, instructorName, 
                    prerequisiteId.isEmpty() ? null : prerequisiteId, maxCapacity);
            subjects.put(subjectId, newSubject);
            
            System.out.println("✅ Subject added successfully!");
            System.out.println("Subject: " + newSubject.subjectName);
            System.out.println("Type: " + newSubject.getCourseType());
            System.out.println("Credits: " + newSubject.credits);
            System.out.println("Capacity: " + (newSubject.maxCapacity == -1 ? "∞" : newSubject.maxCapacity));
            
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid number format");
        }
    }
    
    private static void displayAvailableSubjects(Scanner scanner) {
        System.out.println("\n🔍 AVAILABLE SUBJECTS FOR STUDENT");
        System.out.println("==================================");
        
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine().trim();
        
        if (!students.containsKey(studentId)) {
            System.out.println("❌ Student not found with ID: " + studentId);
            return;
        }
        
        Student student = students.get(studentId);
        System.out.println("Student: " + student.getFullName());
        
        // Get registered subject IDs
        Set<String> registeredSubjects = new HashSet<>();
        for (Registration reg : registrations.values()) {
            if (reg.studentId.equals(studentId) && reg.status == RegistrationStatus.ACTIVE) {
                registeredSubjects.add(reg.subjectId);
            }
        }
        
        System.out.println("\nAvailable Subjects:");
        boolean found = false;
        
        for (Subject subject : subjects.values()) {
            if (!registeredSubjects.contains(subject.subjectId) && subject.isAvailable()) {
                // Check prerequisites
                boolean canRegister = true;
                if (subject.hasPrerequisite()) {
                    canRegister = false;
                    for (Registration reg : registrations.values()) {
                        if (reg.studentId.equals(studentId) && 
                            reg.subjectId.equals(subject.prerequisiteSubjectId) && 
                            reg.status == RegistrationStatus.COMPLETED) {
                            canRegister = true;
                            break;
                        }
                    }
                }
                
                if (canRegister) {
                    System.out.println("  " + subject.subjectId + " - " + subject.subjectName);
                    System.out.println("    Type: " + subject.getCourseType());
                    System.out.println("    Credits: " + subject.credits);
                    System.out.println("    Instructor: " + subject.instructorName);
                    System.out.println("    Available: " + subject.currentEnrollment + "/" + 
                            (subject.maxCapacity == -1 ? "∞" : subject.maxCapacity));
                    if (subject.hasPrerequisite()) {
                        Subject prereq = subjects.get(subject.prerequisiteSubjectId);
                        System.out.println("    Prerequisite: " + prereq.subjectName);
                    }
                    System.out.println("    ---");
                    found = true;
                }
            }
        }
        
        if (!found) {
            System.out.println("  No available subjects found.");
        }
    }
    
    private static void cancelRegistration(Scanner scanner) {
        System.out.println("\n❌ CANCEL REGISTRATION");
        System.out.println("======================");
        
        System.out.print("Enter Registration ID: ");
        String regIdStr = scanner.nextLine().trim();
        
        try {
            Long regId = Long.parseLong(regIdStr);
            Registration reg = registrations.get(regId);
            
            if (reg == null) {
                System.out.println("❌ Registration not found");
                return;
            }
            
            if (reg.status != RegistrationStatus.ACTIVE) {
                System.out.println("❌ Registration is not active");
                return;
            }
            
            Student student = students.get(reg.studentId);
            Subject subject = subjects.get(reg.subjectId);
            
            reg.status = RegistrationStatus.CANCELLED;
            subject.currentEnrollment--;
            
            System.out.println("✅ Registration cancelled successfully!");
            System.out.println("Student: " + student.getFullName());
            System.out.println("Subject: " + subject.subjectName);
            System.out.println("Cancelled on: " + LocalDateTime.now());
            
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid registration ID");
        }
    }
    
    // Inner classes for data models
    static class Student {
        String studentId, title, firstName, lastName, currentSchool, email;
        LocalDate dateOfBirth;
        
        Student(String studentId, String title, String firstName, String lastName, 
                LocalDate dateOfBirth, String currentSchool, String email) {
            this.studentId = studentId;
            this.title = title;
            this.firstName = firstName;
            this.lastName = lastName;
            this.dateOfBirth = dateOfBirth;
            this.currentSchool = currentSchool;
            this.email = email;
        }
        
        String getFullName() {
            return title + " " + firstName + " " + lastName;
        }
        
        int getAge() {
            return LocalDate.now().getYear() - dateOfBirth.getYear();
        }
    }
    
    static class Subject {
        String subjectId, subjectName, instructorName, prerequisiteSubjectId;
        int credits, maxCapacity, currentEnrollment;
        
        Subject(String subjectId, String subjectName, int credits, String instructorName, 
                String prerequisiteSubjectId, int maxCapacity) {
            this.subjectId = subjectId;
            this.subjectName = subjectName;
            this.credits = credits;
            this.instructorName = instructorName;
            this.prerequisiteSubjectId = prerequisiteSubjectId;
            this.maxCapacity = maxCapacity;
            this.currentEnrollment = 0;
        }
        
        boolean isAvailable() {
            return maxCapacity == -1 || currentEnrollment < maxCapacity;
        }
        
        boolean hasPrerequisite() {
            return prerequisiteSubjectId != null && !prerequisiteSubjectId.trim().isEmpty();
        }
        
        String getCourseType() {
            return subjectId.startsWith("0550") ? "Faculty Course" : "General Education Course";
        }
    }
    
    static class Registration {
        Long id;
        String studentId, subjectId;
        LocalDateTime registrationDate;
        RegistrationStatus status;
        
        Registration(String studentId, String subjectId) {
            this.id = registrationIdCounter++;
            this.studentId = studentId;
            this.subjectId = subjectId;
            this.registrationDate = LocalDateTime.now();
            this.status = RegistrationStatus.ACTIVE;
        }
    }
    
    enum RegistrationStatus {
        ACTIVE, CANCELLED, COMPLETED
    }
}
