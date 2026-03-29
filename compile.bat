@echo off
REM Compilation script for Java project
REM Compiles all Java source files from src/ to bin/ directory

echo Compiling Java files...

REM Create bin directory if it doesn't exist
if not exist bin mkdir bin

REM Compile all Java files
javac -d bin src\*.java

if %errorlevel% equ 0 (
    echo.
    echo Compilation successful!
    echo Compiled classes are in the bin/ directory
) else (
    echo.
    echo Compilation failed!
    pause
)
