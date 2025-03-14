package org.example.service.Tasks;

import org.example.DTO.*;

public interface TaskService {
    String addTask(AddTaskRequest addTaskRequest);

    String changeTask(ChangeTaskRequest changeTaskRequest);

    String completeTask(CompleteTaskRequest completeTaskRequest);

    String deleteCompletedTasks(DeleteCompletedTasksRequest deleteCompletedTasksRequest);

    String deleteTask(DeleteTaskRequest deleteTaskRequest);

    GetAllTasksResponse getAllTasks(GetAllTasksRequest getAllTasksRequest);

    GetCompletedTasksResponse getCompletedTasks(GetCompletedTasksRequest getCompletedTasksRequest);

    GetTodayTasksResponse getTodayTasks(GetTodayTasksRequest getTodayTasksRequest);

    String returnCompletedTask(ReturnCompletedTaskRequest request);
}
