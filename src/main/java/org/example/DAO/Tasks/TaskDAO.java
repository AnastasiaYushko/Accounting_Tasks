package org.example.DAO.Tasks;

import org.example.models.Task;

import java.util.ArrayList;

public interface TaskDAO {
    String addTask(int user_id, String title, String date, String time);

    String changeTask(int user_id, int task_id, String title, String date, String time);

    String completeTask(int task_id, int user_id, String complete_date, String complete_time);

    String returnCompletedTask(int task_id, int user_id);

    String deleteCompletedTasks(int user_id);

    String deleteTask(int user_id, int task_id);

    ArrayList<Task> getAllTasks(int user_id);

    ArrayList<Task> getCompletedTasks(int user_id);

    ArrayList<Task> getTodayTasks(int user_id);
}
