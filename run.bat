@echo off
echo Starting Course Registration System...
echo.
echo Please make sure you have Java 8 installed and Maven is available.
echo.
echo If Maven is not installed, you can download it from: https://maven.apache.org/download.cgi
echo.
echo Running Maven build...
call mvn clean install
if %errorlevel% neq 0 (
    echo.
    echo Maven build failed. Please check the error messages above.
    echo Make sure Maven is installed and Java 8 is available.
    pause
    exit /b 1
)
echo.
echo Build successful! Starting the application...
echo.
echo The application will be available at:
echo - API Base URL: http://localhost:8080
echo - Swagger UI: http://localhost:8080/swagger-ui.html
echo - H2 Console: http://localhost:8080/h2-console
echo.
call mvn spring-boot:run
pause
