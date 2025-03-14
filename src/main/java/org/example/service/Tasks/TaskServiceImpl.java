package org.example.service.Tasks;

import org.example.DTO.*;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {
    @Override
    public String addTask(AddTaskRequest addTaskRequest) {
        return "";
    }

    @Override
    public String changeTask(ChangeTaskRequest changeTaskRequest) {
        return "";
    }

    @Override
    public String completeTask(CompleteTaskRequest completeTaskRequest) {
        return "";
    }

    @Override
    public String deleteCompletedTasks(DeleteCompletedTasksRequest deleteCompletedTasksRequest) {
        return "";
    }

    @Override
    public String deleteTask() {
        return "";
    }

    @Override
    public GetAllTasksResponse getAllTasks(GetAllTasksRequest getAllTasksRequest) {
        return null;
    }

    @Override
    public GetCompletedTasksResponse getCompletedTasks(GetCompletedTasksRequest getCompletedTasksRequest) {
        return null;
    }

    @Override
    public GetTodayTasksResponse getTodayTasks(GetTodayTasksRequest getTodayTasksRequest) {
        return null;
    }

    @Override
    public String returnCompletedTask(ReturnCompletedTaskRequest request) {
        return "";
    }
}
