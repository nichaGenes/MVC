# Build stage: compile the Spring Boot application with Maven
FROM maven:3.9.6-eclipse-temurin-8 AS build
WORKDIR /app

# Only copy the pom first to leverage Maven layer caching for dependencies
COPY pom.xml ./

# Copy source code and any additional resources required for the build
COPY src ./src

# Build the application and create the executable jar; skip tests for faster image builds
RUN mvn -B clean package -DskipTests

# Runtime stage: run the packaged Spring Boot application
FROM eclipse-temurin:8-jre
WORKDIR /app

# Copy the built jar from the previous stage
COPY --from=build /app/target/course-registration-system-0.0.1-SNAPSHOT.jar app.jar

# Expose the HTTP port Spring Boot uses by default
EXPOSE 8080

# Launch the application
ENTRYPOINT ["java", "-jar", "app.jar"]
