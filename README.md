# Course Registration System 📝

A comprehensive Java Spring Boot MVC application for managing course registrations for high school students. This system implements the MVC (Model-View-Controller) design pattern and provides a REST API for course registration management.

## Features ✨

- **Student Management**: Create, read, update, and delete student records
- **Subject Management**: Manage elective courses with prerequisites and capacity limits
- **Registration System**: Register students for courses with business rule validation
- **Age Verification**: Students must be at least 15 years old to register
- **Prerequisite Checking**: Automatic validation of course prerequisites
- **Capacity Management**: Track enrollment limits and current capacity
- **REST API**: Complete RESTful API with proper HTTP status codes
- **Data Validation**: Comprehensive input validation using Bean Validation
- **Error Handling**: Global exception handling with meaningful error messages
- **API Documentation**: Swagger/OpenAPI documentation

## Technology Stack 🛠️

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **Spring Security**
- **H2 Database** (In-memory)
- **Maven**
- **Swagger/OpenAPI 3**

## Project Structure 📁

```
src/main/java/com/courseregistration/
├── config/
│   ├── DataInitializer.java          # Sample data initialization
│   ├── OpenApiConfig.java            # Swagger configuration
│   └── SecurityConfig.java           # Security configuration
├── controller/
│   ├── RegistrationController.java   # Registration REST endpoints
│   ├── StudentController.java        # Student REST endpoints
│   └── SubjectController.java        # Subject REST endpoints
├── dto/
│   ├── ApiResponse.java              # Standard API response wrapper
│   ├── RegistrationDTO.java          # Registration data transfer object
│   ├── RegistrationRequestDTO.java   # Registration request DTO
│   ├── StudentDTO.java               # Student data transfer object
│   └── SubjectDTO.java               # Subject data transfer object
├── exception/
│   └── GlobalExceptionHandler.java   # Global exception handling
├── model/
│   ├── Registration.java             # Registration entity
│   ├── RegistrationStatus.java       # Registration status enum
│   ├── Student.java                  # Student entity
│   └── Subject.java                  # Subject entity
├── repository/
│   ├── RegistrationRepository.java   # Registration data access
│   ├── StudentRepository.java        # Student data access
│   └── SubjectRepository.java        # Subject data access
├── service/
│   ├── RegistrationService.java       # Registration business logic
│   ├── StudentService.java           # Student business logic
│   └── SubjectService.java           # Subject business logic
└── CourseRegistrationSystemApplication.java
```

## Data Models 📦

### Student Entity
- **studentId**: String (8 digits, must start with "69")
- **title**: String (Mr., Ms., etc.)
- **firstName**: String
- **lastName**: String
- **dateOfBirth**: LocalDate
- **currentSchool**: String
- **email**: String (unique)

### Subject Entity
- **subjectId**: String (8 digits, starts with "0550" for faculty courses or "9069" for general education)
- **subjectName**: String
- **credits**: Integer (must be > 0)
- **instructorName**: String
- **prerequisiteSubjectId**: String (optional)
- **maxCapacity**: Integer (-1 for unlimited, otherwise positive number)
- **currentEnrollment**: Integer

### Registration Entity
- **id**: Long (auto-generated)
- **studentId**: String
- **subjectId**: String
- **registrationDate**: LocalDateTime
- **status**: RegistrationStatus (ACTIVE, CANCELLED, COMPLETED)

## Business Rules ⚙️

1. **Age Verification**: Students must be at least 15 years old to register
2. **Prerequisite Check**: Students must have completed prerequisite courses before registering
3. **Capacity Management**: Course enrollment cannot exceed maximum capacity
4. **Duplicate Prevention**: Students cannot register for the same course twice
5. **ID Validation**: Student IDs must start with "69", Subject IDs with "0550" or "9069"

## API Endpoints 🌐

### Students
- `GET /api/students` - Get all students
- `GET /api/students/{studentId}` - Get student by ID
- `GET /api/students/email/{email}` - Get student by email
- `GET /api/students/school/{school}` - Get students by school
- `GET /api/students/search?name={name}` - Search students by name
- `POST /api/students` - Create new student
- `PUT /api/students/{studentId}` - Update student
- `DELETE /api/students/{studentId}` - Delete student
- `GET /api/students/{studentId}/eligible` - Check student eligibility

### Subjects
- `GET /api/subjects` - Get all subjects
- `GET /api/subjects/{subjectId}` - Get subject by ID
- `GET /api/subjects/available` - Get available subjects
- `GET /api/subjects/no-prerequisites` - Get subjects without prerequisites
- `GET /api/subjects/faculty` - Get faculty courses
- `GET /api/subjects/general-education` - Get general education courses
- `GET /api/subjects/search?name={name}` - Search subjects by name
- `GET /api/subjects/instructor/{instructorName}` - Get subjects by instructor
- `GET /api/subjects/credits/{credits}` - Get subjects by credits
- `POST /api/subjects` - Create new subject
- `PUT /api/subjects/{subjectId}` - Update subject
- `DELETE /api/subjects/{subjectId}` - Delete subject
- `GET /api/subjects/{subjectId}/available` - Check subject availability

### Registrations
- `GET /api/registrations` - Get all registrations
- `GET /api/registrations/student/{studentId}` - Get registrations by student
- `GET /api/registrations/student/{studentId}/active` - Get active registrations by student
- `GET /api/registrations/subject/{subjectId}` - Get registrations by subject
- `GET /api/registrations/subject/{subjectId}/active` - Get active registrations by subject
- `POST /api/registrations/register` - Register student for course
- `PUT /api/registrations/{registrationId}/cancel` - Cancel registration
- `PUT /api/registrations/{registrationId}/complete` - Complete registration
- `GET /api/registrations/student/{studentId}/available` - Get available subjects for student

## Getting Started 🚀

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd course-registration-system
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - API Base URL: `http://localhost:8080`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`
   - H2 Console: `http://localhost:8080/h2-console`

### Docker Setup 🐳

You can run the application without installing Java or Maven locally by using Docker:

1. **Build the image**
   ```bash
   docker build -t course-registration .
   ```

2. **Start the container**
   ```bash
   docker run --rm -p 8080:8080 course-registration
   ```

3. **Access the services** (same as above)
   - API Base URL: `http://localhost:8080`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`
   - H2 Console: `http://localhost:8080/h2-console`

To rebuild after code changes, rerun the `docker build` command.

### Docker Compose 🧩

Prefer a single command workflow? Use Docker Compose:

1. **Start (builds automatically on first run)**
   ```bash
   docker compose up --build
   ```

2. **Stop** (in another terminal)
   ```bash
   docker compose down
   ```

All endpoints remain available on `http://localhost:8080` while the `app` service is running.

The Compose stack launches a PostgreSQL database (`db` service) alongside the Spring Boot app. Default credentials:
- Database: `course_registration`
- User: `course_user`
- Password: `course_password`

Spring Boot auto-creates and updates the schema through JPA (`ddl-auto=update`) and the `DataInitializer` seeds the sample rows the first time the app starts.

## Sample Data 📊

The application comes with pre-loaded sample data:

### Sample Students
- John Doe (69000001) - Springfield High School
- Jane Smith (69000002) - Springfield High School
- Mike Johnson (69000003) - Lincoln High School
- Sarah Wilson (69000004) - Springfield High School
- David Brown (69000005) - Washington High School

### Sample Subjects
**Faculty Courses (0550xxxx):**
- Advanced Mathematics (05500001) - 3 credits
- Physics Fundamentals (05500002) - 3 credits (requires Advanced Mathematics)
- General Chemistry (05500003) - 3 credits (requires Advanced Mathematics)
- Biology Basics (05500004) - 3 credits
- Introduction to Programming (05500005) - 3 credits

**General Education Courses (9069xxxx):**
- English Literature (90690001) - 2 credits
- World History (90690002) - 2 credits
- Art and Design (90690003) - 2 credits
- Music Appreciation (90690004) - 2 credits
- Physical Education (90690005) - 1 credit
- Introduction to Psychology (90690006) - 2 credits
- Basic Economics (90690007) - 2 credits
- Geography (90690008) - 2 credits

## API Usage Examples 💡

### Register a Student for a Course
```bash
curl -X POST http://localhost:8080/api/registrations/register \
  -H "Content-Type: application/json" \
  -d '{
    "studentId": "69000001",
    "subjectId": "05500001"
  }'
```

### Get Available Subjects for a Student
```bash
curl http://localhost:8080/api/registrations/student/69000001/available
```

### Create a New Student
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "studentId": "69000006",
    "title": "Ms.",
    "firstName": "Emma",
    "lastName": "Davis",
    "dateOfBirth": "2005-05-15",
    "currentSchool": "Springfield High School",
    "email": "emma.davis@email.com"
  }'
```

## Testing 🧪

The application includes comprehensive validation and error handling. Test various scenarios:

1. **Age Validation**: Try registering a student under 15 years old
2. **Prerequisite Check**: Try registering for Physics without completing Advanced Mathematics
3. **Capacity Limits**: Register students until course capacity is reached
4. **Duplicate Registration**: Try registering the same student for the same course twice

## Database Schema 🗄️

The application uses H2 in-memory database with the following tables:
- `students` - Student information
- `subjects` - Course/subject information
- `registrations` - Student-course registration records

## Security 🔒

- Basic authentication configured (currently disabled for API endpoints)
- CORS enabled for cross-origin requests
- Input validation using Bean Validation annotations
- SQL injection protection through JPA

## Contributing 🤝

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License 📄

This project is licensed under the MIT License - see the LICENSE file for details.

## Support 📞

For support and questions, please contact the development team or create an issue in the repository.
