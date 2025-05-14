package app;

import java.util.ArrayList;

public class ToDoList {
    private ArrayList<String> tasks;

    public ToDoList() {
        tasks = new ArrayList<>();
    }

    public void addTask(String task) {
        tasks.add(task);
    }

    public void removeTask(String task) {   
        tasks.remove(task);
    }

    public ArrayList<String> getTasks() {
        return tasks;
    }
}
