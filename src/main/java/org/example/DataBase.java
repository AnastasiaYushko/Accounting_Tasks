package org.example;

import org.example.models.Task;
import org.example.models.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataBase {
    private final Map<Integer, User> users;
    private Integer keyUser;
    private final Map<Integer, ArrayList<Task>> user_tasks;

    public DataBase() {
        users = new HashMap<>();
        user_tasks = new HashMap<>();
        keyUser = 1;
    }

    public String addTask(Task task) {return "";}

    public String addUser(User user) {return "";}

    public String changeTask(Task task) {return "";}

    public String completeTask(int task_id) {return "";}

    public String deleteCompletedTasks(int user_id) {return "";}

    public String deleteTask() {return "";}

    public String deleteUser() {return "";}

    public ArrayList<Task> getAllTasks() {return new ArrayList<>();}

    public ArrayList<Task> getCompletedTasks() {return new ArrayList<>();}

    public ArrayList<Task> getTodayTasks() {return new ArrayList<>();}

    public User getUser() {return new User();}

    public String returnCompletedTask() {return "";}
}
