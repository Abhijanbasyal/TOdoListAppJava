@echo off
echo Compiling the Java code...
javac -d bin -sourcepath src src/main/java/app/*.java
echo Done.
pause
