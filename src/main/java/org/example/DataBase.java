package org.example;

import org.example.enums.Status;
import org.example.models.Task;
import org.example.models.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Component
public class DataBase {
    private final Map<Integer, User> users;
    private Integer keyUser;
    private Integer keyTask;
    private final HashMap<Integer, HashMap<Integer, Task>> user_tasks;

    public DataBase() {
        users = new HashMap<>();
        user_tasks = new HashMap<>();
        keyUser = 1;
        keyTask = 1;
    }

    public String addTask(Task task) throws NoSuchElementException{
        int user_id = task.getUser_id();
        if (!users.containsKey(user_id)){
            throw new NoSuchElementException("Пользователь не найден");
        }
        task.setId(keyTask);
        task.setStatus(Status.active);
        synchronized (user_tasks) {
            HashMap<Integer, Task> tasks = user_tasks.get(user_id);
            tasks.put(keyTask, task);
            user_tasks.put(user_id, tasks);
            keyTask++;
        }
        return "Задача добавлена id = " + task.getId();
    }

    public int addUser(User user) throws NoSuchElementException {
        synchronized (users) {
            for (User user1 : users.values()) {
                if (user.getLogin().equals(user1.getLogin())) {
                    throw new NoSuchElementException("Пользователь c таким логином уже существует");
                }
            }
            user.setId(keyUser);
            users.put(keyUser, user);
            keyUser++;
        }
        synchronized (user_tasks) {
            user_tasks.put(user.getId(), new HashMap<>());
        }
        return user.getId();
    }

    public String changeTask(Task task) throws NoSuchElementException {
        int user_id = task.getUser_id();
        if (!users.containsKey(user_id)){
            throw new NoSuchElementException("Пользователь не найден");
        }
        synchronized (user_tasks) {
            HashMap<Integer, Task> tasks = user_tasks.get(user_id);
            if (tasks != null && tasks.containsKey(task.getId())) {
                Task oldTask = tasks.get(user_id);
                if (oldTask.equals(task)) {
                    throw new NoSuchElementException("Новый изменения не найдены");
                }
                tasks.put(task.getId(), task);
                user_tasks.put(user_id, tasks);
            } else {
                throw new NoSuchElementException("Задача не найдена");
            }
        }
        return "Задача изменена";
    }

    public String completeTask(int user_id, int task_id) throws NoSuchElementException{
        if (!users.containsKey(user_id)){
            throw new NoSuchElementException("Пользователь не найден");
        }
        synchronized (user_tasks) {
            HashMap<Integer, Task> tasks = user_tasks.get(user_id);
            if (tasks != null && tasks.containsKey(task_id)) {
                Task task = tasks.get(task_id);
                if (task.getStatus() == Status.completed) {
                    throw new NoSuchElementException("Задача уже выполнена");
                }
                task.setStatus(Status.completed);
                tasks.put(task.getId(), task);
                user_tasks.put(user_id, tasks);
            } else {
                throw new NoSuchElementException("Задача не найдена");
            }
            return "Задача выполнена";
        }
    }

    public String deleteCompletedTasks(int user_id) throws NoSuchElementException {
        if (!users.containsKey(user_id)){
            throw new NoSuchElementException("Пользователь не найден");
        }
        boolean flag = false;
        synchronized (user_tasks) {
            HashMap<Integer, Task> tasks = user_tasks.get(user_id);
            for (Task task : tasks.values()) {
                if (task.getStatus().equals(Status.completed)) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                tasks.values().removeIf(task -> (task.getStatus() == Status.completed));
                user_tasks.put(user_id, tasks);
                return "Задачи удалены";
            } else {
                throw new NoSuchElementException("Нет выполненных задач");
            }
        }
    }

    public String deleteTask(int user_id, int task_id) throws NoSuchElementException{
        if (!users.containsKey(user_id)){
            throw new NoSuchElementException("Пользователь не найден");
        }
        synchronized (user_tasks) {
            HashMap<Integer, Task> tasks = user_tasks.get(user_id);
            if (tasks != null && tasks.containsKey(task_id)) {
                tasks.remove(task_id);
                user_tasks.put(user_id, tasks);
                return "Задача удалена";
            } else {
                throw new NoSuchElementException("Задача не найдена");
            }
        }
    }

    public String deleteUser(int user_id) throws NoSuchElementException {
        synchronized (user_tasks) {
            user_tasks.remove(user_id);
        }
        synchronized (users) {
            if (!users.containsKey(user_id)) {
                throw new NoSuchElementException("Пользователь не найден");
            }
            users.remove(user_id);
        }
        return "Пользователь удален";
    }

    public ArrayList<Task> getAllTasks(int user_id) throws NoSuchElementException {
        if (!users.containsKey(user_id)){
            throw new NoSuchElementException("Пользователь не найден");
        }
        return new ArrayList<>(user_tasks.get(user_id).values());
    }

    public ArrayList<Task> getCompletedTasks(int user_id) throws NoSuchElementException {
        if (!users.containsKey(user_id)){
            throw new NoSuchElementException("Пользователь не найден");
        }
        synchronized (user_tasks) {
            HashMap<Integer, Task> tasks = user_tasks.get(user_id);
            ArrayList<Task> arrayList = new ArrayList<>();
            for (Task task : tasks.values()) {
                if (task.getStatus() == Status.completed) {
                    arrayList.add(task);
                }
            }
            return arrayList;
        }
    }

    public ArrayList<Task> getTodayTasks(int user_id) throws NoSuchElementException{
        if (!users.containsKey(user_id)){
            throw new NoSuchElementException("Пользователь не найден");
        }
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = today.format(formatter);
        HashMap<Integer, Task> tasks = user_tasks.get(user_id);
        ArrayList<Task> todayTasks = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task.getDate().equals(formattedDate)){
                todayTasks.add(task);
            }
        }
        return todayTasks;
    }

    public User getUser(String login, String password) throws NoSuchElementException {
        synchronized (users) {
            for (User user : users.values()) {
                if (login.equals(user.getLogin()) && password.equals(user.getPassword())) {
                    return user;
                }
            }
        }
        throw new NoSuchElementException("Пользователь не найден");
    }

    public String returnCompletedTask(int user_id, int task_id) throws NoSuchElementException{
        if (!users.containsKey(user_id)){
            throw new NoSuchElementException("Пользователь не найден");
        }
        synchronized (user_tasks) {
            HashMap<Integer, Task> tasks = user_tasks.get(user_id);
            if (tasks != null && tasks.containsKey(task_id)) {
                Task task = tasks.get(task_id);
                if (task.getStatus() == Status.active){
                    return "Задача уже активна";
                }
                task.setStatus(Status.active);
                tasks.put(task.getId(), task);
                user_tasks.put(user_id, tasks);
                return "Задача теперь активна";
            } else {
                throw new NoSuchElementException("Задача не найдена");
            }
        }
    }
}