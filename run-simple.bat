@echo off
echo 🎓 Course Registration System - Simple Version
echo ==============================================
echo.

REM Try to find Java installation
echo 🔍 Looking for Java installation...

REM Check common Java installation paths
set JAVA_HOME=
if exist "C:\Program Files\Java\jdk1.8.0_*" (
    for /d %%i in ("C:\Program Files\Java\jdk1.8.0_*") do set JAVA_HOME=%%i
)
if exist "C:\Program Files (x86)\Java\jdk1.8.0_*" (
    for /d %%i in ("C:\Program Files (x86)\Java\jdk1.8.0_*") do set JAVA_HOME=%%i
)

if "%JAVA_HOME%"=="" (
    echo ❌ Java JDK not found in standard locations.
    echo Please install Java JDK 8 or set JAVA_HOME environment variable.
    echo.
    echo You can download Java JDK from: https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html
    pause
    exit /b 1
)

echo ✅ Found Java at: %JAVA_HOME%

REM Add Java to PATH
set PATH=%JAVA_HOME%\bin;%PATH%

echo.
echo 🔨 Compiling the application...
javac SimpleCourseRegistrationApp.java

if %errorlevel% neq 0 (
    echo ❌ Compilation failed. Please check the error messages above.
    pause
    exit /b 1
)

echo ✅ Compilation successful!
echo.
echo 🚀 Starting Course Registration System...
echo ==============================================
echo.

java SimpleCourseRegistrationApp

echo.
echo 👋 Thank you for using Course Registration System!
pause
