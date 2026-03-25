@echo off
echo ========================================
echo   Library Management System
echo ========================================
echo.

REM Compile all Java files
echo [1/2] Compiling...
javac -cp ".;lib/mssql-jdbc.jar" ASM/database/*.java ASM/phase1/*.java

if %errorlevel% neq 0 (
    echo.
    echo [ERROR] Compilation failed!
    pause
    exit /b 1
)

echo [OK] Compilation successful!
echo.

REM Run the main program
echo [2/2] Running program...
echo.
java -cp ".;lib/mssql-jdbc.jar" ASM.phase1.LibraryManagementPhase1

pause
