package org.example;

import org.example.enums.Status;
import org.example.models.Task;
import org.example.models.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Component
public class DataBase {
    private final Map<Integer, User> users;
    private Integer keyUser;
    private Integer keyTask;
    private final Map<Integer, ArrayList<Task>> user_tasks;

    public DataBase() {
        users = new HashMap<>();
        user_tasks = new HashMap<>();
        keyUser = 1;
        keyTask = 1;
    }

    public String addTask(Task task) {
        int user_id = task.getUser_id();
        task.setId(keyTask);
        task.setStatus(Status.active);
        synchronized (user_tasks) {
            ArrayList<Task> tasks = user_tasks.get(user_id);
            tasks.add(task.getId(), task);
            user_tasks.put(user_id, tasks);
        }
            keyTask++;
            return "Задача добавлена";
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
            user_tasks.put(user.getId(), new ArrayList<Task>());
        }
        return user.getId();
    }

    public String changeTask(Task task) throws NoSuchElementException  {
        int user_id = task.getUser_id();
        synchronized (user_tasks) {
            ArrayList<Task> tasks = user_tasks.get(user_id);
            Task oldTask = tasks.get(user_id);
            if (oldTask.equals(task)) {
                throw new NoSuchElementException("Новый изменения не найдены");
            }
            tasks.add(task.getId(), task);
            user_tasks.put(user_id, tasks);
        }
        return "Задача изменена";
    }

    public String completeTask(int user_id, int task_id) {
        synchronized (user_tasks) {
            ArrayList<Task> tasks = user_tasks.get(user_id);
            Task task = tasks.get(task_id);
            task.setStatus(Status.completed);
            tasks.add(task.getId(), task);
            user_tasks.put(user_id, tasks);
            return "Задача выполнена";
        }
    }

    public String deleteCompletedTasks(int user_id) throws NoSuchElementException {
        boolean flag = false;
        synchronized (user_tasks) {
            ArrayList<Task> tasks = user_tasks.get(user_id);
            for (Task task : tasks) {
                if (task.getStatus().equals(Status.completed)) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                tasks.removeIf(task -> task.getStatus().equals(Status.completed));
                user_tasks.put(user_id, tasks);
                return "Задачи удалены";
            } else {
                throw new NoSuchElementException("Нет выполненных задач");
            }
        }
    }

    public String deleteTask(int user_id, int task_id) {
        synchronized (user_tasks) {
            ArrayList<Task> tasks = user_tasks.get(user_id);
            Task task = tasks.get(task_id);
            tasks.remove(task);
            user_tasks.put(user_id, tasks);
            return "Задача удалена";
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

    public ArrayList<Task> getAllTasks(int user_id) {
        return user_tasks.get(user_id);
    }

    public ArrayList<Task> getCompletedTasks(int user_id) {
        synchronized (user_tasks) {
            ArrayList<Task> tasks = user_tasks.get(user_id);
            for (Task task : tasks) {
                if (task.getStatus().equals(Status.completed)) {
                    tasks.add(task);
                }
            }
            return tasks;
        }
    }

    public ArrayList<Task> getTodayTasks(int user_id) {
        return new ArrayList<>();
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

    public String returnCompletedTask(int user_id, int task_id) {
        synchronized (user_tasks) {
            ArrayList<Task> tasks = user_tasks.get(user_id);
            Task task = tasks.get(task_id);
            task.setStatus(Status.active);
            tasks.add(task.getId(), task);
            user_tasks.put(user_id, tasks);
            return "Задача активна";
        }
    }
}