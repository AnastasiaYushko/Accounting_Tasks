package org.example.service.Tasks;

import org.example.DAO.Tasks.TaskDAOImpl;
import org.example.DTO.*;
import org.example.SpringConfig;
import org.example.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskDAOImpl taskDAO;

    @Autowired
    public TaskServiceImpl(TaskDAOImpl taskDAO) {
        this.taskDAO = taskDAO;
    }

    @Override
    public String addTask(AddTaskRequest addTaskRequest) {
        return taskDAO.addTask(addTaskRequest.getUser_id(), addTaskRequest.getTitle(), addTaskRequest.getDate(), addTaskRequest.getTime());
    }

    @Override
    public String changeTask(ChangeTaskRequest changeTaskRequest) {
        return taskDAO.changeTask(changeTaskRequest.getUser_id(), changeTaskRequest.getTask_id(), changeTaskRequest.getTitle(), changeTaskRequest.getDate(), changeTaskRequest.getTime());
    }

    @Override
    public String completeTask(CompleteTaskRequest completeTaskRequest) {
        return taskDAO.completeTask(completeTaskRequest.getTask_id(), completeTaskRequest.getUser_id(),completeTaskRequest.getComplete_date(),completeTaskRequest.getComplete_time());
    }

    @Override
    public String deleteCompletedTasks(DeleteCompletedTasksRequest deleteCompletedTasksRequest) {
        return taskDAO.deleteCompletedTasks(deleteCompletedTasksRequest.getUser_id());
    }

    @Override
    public String deleteTask(DeleteTaskRequest deleteTaskRequest) {
        return taskDAO.deleteTask(deleteTaskRequest.getUser_id(), deleteTaskRequest.getTask_id());
    }

    @Override
    public GetAllTasksResponse getAllTasks(GetAllTasksRequest getAllTasksRequest) {
        GetAllTasksResponse getAllTasksResponse = SpringConfig.getContext().getBean("getAllTasksResponse", GetAllTasksResponse.class);
        ArrayList<Task> tasks = taskDAO.getAllTasks(getAllTasksRequest.getUser_id());

        ArrayList<String> newListTasks = new ArrayList<>();

        for (Task task : tasks) {
            newListTasks.add(task.toString());
        }

        getAllTasksResponse.setTasks(newListTasks);
        return getAllTasksResponse;
    }

    @Override
    public GetCompletedTasksResponse getCompletedTasks(GetCompletedTasksRequest getCompletedTasksRequest) {
        GetCompletedTasksResponse getCompletedTasksResponse = SpringConfig.getContext().getBean("getCompletedTasksResponse", GetCompletedTasksResponse.class);
        ArrayList<Task> tasks = taskDAO.getCompletedTasks(getCompletedTasksRequest.getUser_id());

        ArrayList<String> newListTasks = new ArrayList<>();

        for (Task task : tasks) {
            newListTasks.add(task.toString());
        }

        getCompletedTasksResponse.setTasks(newListTasks);
        return getCompletedTasksResponse;
    }

    @Override
    public GetTodayTasksResponse getTodayTasks(GetTodayTasksRequest getTodayTasksRequest) {
        GetTodayTasksResponse getTodayTasksResponse = SpringConfig.getContext().getBean("getTodayTasksResponse", GetTodayTasksResponse.class);
        ArrayList<Task> tasks = taskDAO.getTodayTasks(getTodayTasksRequest.getUser_id());

        ArrayList<String> newListTasks = new ArrayList<>();

        for (Task task : tasks) {
            newListTasks.add(task.toString());
        }

        getTodayTasksResponse.setTasks(newListTasks);
        return getTodayTasksResponse;
    }

    @Override
    public String returnCompletedTask(ReturnCompletedTaskRequest request) {
        return taskDAO.returnCompletedTask(request.getTask_id(), request.getUser_id());
    }
}
