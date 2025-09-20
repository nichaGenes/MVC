# How to Run the Course Registration System

## Prerequisites

1. **Java 8** - Make sure Java 8 is installed on your system
2. **Maven** - Download and install Maven from https://maven.apache.org/download.cgi

## Quick Start

### Option 1: Using the Batch File (Windows)
1. Double-click `run.bat`
2. The script will automatically build and run the application

### Option 2: Using Command Line
1. Open Command Prompt or PowerShell
2. Navigate to the project directory
3. Run the following commands:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## Accessing the Application

Once the application starts successfully, you can access:

- **API Base URL**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **H2 Database Console**: http://localhost:8080/h2-console

## Sample Data

The application comes with pre-loaded sample data:

### Students
- John Doe (69000001) - Springfield High School
- Jane Smith (69000002) - Springfield High School  
- Mike Johnson (69000003) - Lincoln High School
- Sarah Wilson (69000004) - Springfield High School
- David Brown (69000005) - Washington High School

### Subjects
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

## Testing the API

### Register a Student for a Course
```bash
curl -X POST http://localhost:8080/api/registrations/register \
  -H "Content-Type: application/json" \
  -d '{
    "studentId": "69000001",
    "subjectId": "05500001"
  }'
```

### Get All Students
```bash
curl http://localhost:8080/api/students
```

### Get All Subjects
```bash
curl http://localhost:8080/api/subjects
```

## Troubleshooting

### Maven Not Found
If you get "mvn is not recognized", you need to:
1. Download Maven from https://maven.apache.org/download.cgi
2. Extract it to a folder (e.g., C:\apache-maven-3.8.6)
3. Add the bin folder to your PATH environment variable
4. Restart your command prompt

### Java Version Issues
Make sure you have Java 8 installed:
```bash
java -version
```
Should show version 1.8.x

### Port Already in Use
If port 8080 is already in use, you can change it in `src/main/resources/application.yml`:
```yaml
server:
  port: 8081  # Change to a different port
```

## Features

- ✅ Student Management (CRUD operations)
- ✅ Subject Management (CRUD operations)  
- ✅ Course Registration with business rules
- ✅ Age verification (students must be 15+)
- ✅ Prerequisite checking
- ✅ Capacity management
- ✅ REST API with proper HTTP status codes
- ✅ Input validation
- ✅ Error handling
- ✅ Swagger documentation
- ✅ H2 in-memory database
- ✅ Sample data initialization
