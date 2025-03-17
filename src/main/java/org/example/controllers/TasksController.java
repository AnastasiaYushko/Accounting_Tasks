package org.example.controllers;

import jakarta.validation.Valid;
import org.example.DTO.*;
import org.example.service.Tasks.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = "*")
@Validated
public class TasksController {
    private final TaskServiceImpl taskService;

    @Autowired
    public TasksController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/addTask")
    public ResponseEntity<?> addTask(@Valid @RequestBody AddTaskRequest addTaskRequest) {
        try {
            String result = taskService.addTask(addTaskRequest);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/changeTask")
    public ResponseEntity<?> changeTask(@Valid @RequestBody ChangeTaskRequest changeTaskRequest) {
        try {
            String result = taskService.changeTask(changeTaskRequest);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/completeTask")
    public ResponseEntity<?> completeTask(@Valid @RequestBody CompleteTaskRequest completeTaskRequest) {
        try {
            String result = taskService.completeTask(completeTaskRequest);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/returnCompletedTask")
    public ResponseEntity<?> returnCompletedTask(@Valid @RequestBody ReturnCompletedTaskRequest returnCompletedTaskRequest) {
        try {
            String result = taskService.returnCompletedTask(returnCompletedTaskRequest);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //можно изменить
    @DeleteMapping("/deleteCompletedTasks")
    public ResponseEntity<?> deleteCompletedTasks(@Valid @RequestBody DeleteCompletedTasksRequest deleteCompletedTasksRequest) {
        try {
            String result = taskService.deleteCompletedTasks(deleteCompletedTasksRequest);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteTask")
    public ResponseEntity<?> deleteTask(@Valid @RequestBody DeleteTaskRequest deleteTaskRequest) {
        try {
            String result = taskService.deleteTask(deleteTaskRequest);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllTasks")
    public ResponseEntity<?> getAllTasks(@Valid @RequestBody GetAllTasksRequest getAllTasksRequest) {
        try {
            GetAllTasksResponse result = taskService.getAllTasks(getAllTasksRequest);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getCompletedTasks")
    public ResponseEntity<?> getCompletedTasks(@Valid @RequestBody GetCompletedTasksRequest getCompletedTasksRequest) {
        try {
            GetCompletedTasksResponse result = taskService.getCompletedTasks(getCompletedTasksRequest);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getTodayTasks")
    public ResponseEntity<?> getTodayTasks(@Valid @RequestBody GetTodayTasksRequest getTodayTasksRequest) {
        try {
            GetTodayTasksResponse result = taskService.getTodayTasks(getTodayTasksRequest);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
