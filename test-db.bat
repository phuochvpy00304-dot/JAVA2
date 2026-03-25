@echo off
echo Testing Database Connection...
echo.
javac -cp ".;lib/mssql-jdbc.jar" ASM/database/DatabaseConnection.java ASM/database/TestConnection.java
java -cp ".;lib/mssql-jdbc.jar" ASM.database.TestConnection
pause
