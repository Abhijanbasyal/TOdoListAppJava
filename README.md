@echo off
rem Building the project
echo Building ToDo List App...
javac -d bin -sourcepath src/ src/main/java/app/*.java
echo Build Complete!
pause



@echo off
rem Running the ToDo List App
echo Running ToDo List App...
java -cp bin app.ToDoApp
pause
