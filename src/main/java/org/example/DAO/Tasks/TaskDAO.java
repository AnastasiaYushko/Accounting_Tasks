package org.example.DAO.Tasks;

import org.example.models.Task;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface TaskDAO {
     int addTask(int user_id,String title, Date date, Time time, String status);
     String changeTask(int user_id, int task_id, String title, Date date, Time time);
     //Изменение статуса
     String completeTask(int task_id, int user_id);
     String returnCompletedTask(int task_id, int user_id);
     //
     String deleteCompletedTasks(List<Integer> task_id);
     String deleteTask(int task_id);
     List<Task> getAllTasks();
     List<Task> getCompletedTasks();
     List<Task> getTodayTasks();
}
