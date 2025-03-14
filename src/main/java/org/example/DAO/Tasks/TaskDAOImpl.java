package org.example.DAO.Tasks;

import org.example.models.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class TaskDAOImpl implements TaskDAO {
    @Override
    public String addTask(int user_id, String title, String date, String time) {
        return "";
    }

    @Override
    public String changeTask(int user_id, int task_id, String title, String date, String time) {
        return "";
    }

    @Override
    public String completeTask(int task_id, int user_id) {
        return "";
    }

    @Override
    public String returnCompletedTask(int task_id, int user_id) {
        return "";
    }

    @Override
    public String deleteCompletedTasks(int user_id) {
        return "";
    }

    @Override
    public String deleteTask(int user_id, int task_id) {
        return "";
    }

    @Override
    public ArrayList<Task> getAllTasks(int user_id) {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<Task> getCompletedTasks(int user_id) {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<Task> getTodayTasks(int user_id) {
        return new ArrayList<>();
    }
}
