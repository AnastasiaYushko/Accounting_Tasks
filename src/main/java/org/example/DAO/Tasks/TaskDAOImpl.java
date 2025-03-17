package org.example.DAO.Tasks;

import org.example.DataBase;
import org.example.SpringConfig;
import org.example.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class TaskDAOImpl implements TaskDAO {

    @Autowired
    private final DataBase dataBase;

    public TaskDAOImpl(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public String addTask(int user_id, String title, String date, String time) {
        Task task = SpringConfig.getContext().getBean("task",Task.class);
        task.setUser_id(user_id);
        task.setTitle(title);
        task.setDate(date);
        task.setTime(time);
        return dataBase.addTask(task);
    }

    @Override
    public String changeTask(int user_id, int task_id, String title, String date, String time) {
        Task task = SpringConfig.getContext().getBean("task",Task.class);
        task.setId(task_id);
        task.setUser_id(user_id);
        task.setTitle(title);
        task.setDate(date);
        task.setTime(time);
        return dataBase.changeTask(task);
    }

    @Override
    public String completeTask(int task_id, int user_id) {
        return dataBase.completeTask(user_id,task_id);
    }

    @Override
    public String returnCompletedTask(int task_id, int user_id) {
        return dataBase.returnCompletedTask(user_id,task_id);
    }

    @Override
    public String deleteCompletedTasks(int user_id) {
        return dataBase.deleteCompletedTasks(user_id);
    }

    @Override
    public String deleteTask(int user_id, int task_id) {
        return dataBase.deleteTask(user_id,task_id);
    }

    @Override
    public ArrayList<Task> getAllTasks(int user_id) {
        return dataBase.getAllTasks(user_id);
    }

    @Override
    public ArrayList<Task> getCompletedTasks(int user_id) {
        return dataBase.getCompletedTasks(user_id);
    }

    @Override
    public ArrayList<Task> getTodayTasks(int user_id) {
        return dataBase.getTodayTasks(user_id);
    }
}
