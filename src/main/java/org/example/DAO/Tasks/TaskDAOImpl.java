package org.example.DAO.Tasks;

import org.example.models.Task;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Repository
public class TaskDAOImpl implements TaskDAO{
    @Override
    public int addTask(int user_id, String title, Date date, Time time, String status) {
        return 0;
    }

    @Override
    public String changeTask(int user_id, int task_id, String title, Date date, Time time) {
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
    public String deleteCompletedTasks(List<Integer> task_id) {
        return "";
    }

    @Override
    public String deleteTask(int task_id) {
        return "";
    }

    @Override
    public List<Task> getAllTasks() {
        return List.of();
    }

    @Override
    public List<Task> getCompletedTasks() {
        return List.of();
    }

    @Override
    public List<Task> getTodayTasks() {
        return List.of();
    }
}
