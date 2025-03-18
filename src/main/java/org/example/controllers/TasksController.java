package org.example.controllers;

import jakarta.validation.Valid;
import org.example.DTO.*;
import org.example.enums.Status;
import org.example.models.Task;
import org.example.models.User;
import org.example.service.TaskService;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TasksController {

    private final TaskService taskService;
    private final UserService userService;

    @PostMapping("/addTask")
    public ResponseEntity<?> addTask(@Valid @RequestBody AddTaskRequest addTaskRequest) {
        try {
            Task task = new Task();
            task.setTitle(addTaskRequest.getTitle());
            task.setStatus(Status.active);
            task.setDate(addTaskRequest.getDate());
            task.setTime(addTaskRequest.getTime());

            Optional<User> userOptional = userService.getUserById(addTaskRequest.getUser_id());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                task.setUser(user); // Связываем задачу с пользователем
            } else {
                return new ResponseEntity<>("Пользователь с ID " + addTaskRequest.getUser_id() + " не найден", HttpStatus.NOT_FOUND);
            }
            Task addedTask = taskService.addTask(task);
            return new ResponseEntity<>(addedTask.getId(), HttpStatus.CREATED); // Вернуть ID и код 201
        } catch (Exception e) {
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/changeTask")
    public ResponseEntity<?> changeTask(@Valid @RequestBody ChangeTaskRequest changeTaskRequest) {
        try {
            Optional<Task> changedTask = taskService.changeTask(
                    changeTaskRequest.getUser_id(),
                    changeTaskRequest.getTask_id(),
                    changeTaskRequest.getTitle(),
                    changeTaskRequest.getDate(),
                    changeTaskRequest.getTime()
            );
            if (changedTask.isPresent()) {
                return new ResponseEntity<>(changedTask.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Задача не найдена или не принадлежит пользователю", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/completeTask/{taskId}/{userId}")
    public ResponseEntity<?> completeTask(@PathVariable Long taskId, @PathVariable Long userId) {
        try {
            taskService.completeTask(taskId, userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Код 204
        } catch (Exception e) {
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/returnCompletedTask/{taskId}/{userId}")
    public ResponseEntity<?> returnCompletedTask(@PathVariable Long taskId, @PathVariable Long userId) {
        try {
            taskService.returnCompletedTask(taskId, userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Код 204
        } catch (Exception e) {
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteCompletedTasks/{userId}")
    public ResponseEntity<?> deleteCompletedTasks(@PathVariable Long userId) {
        try {
            taskService.deleteCompletedTasks(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Код 204
        } catch (Exception e) {
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteTask/{taskId}/{userId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId, @PathVariable Long userId) {
        try {
            taskService.deleteTask(taskId, userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Код 204
        } catch (Exception e) {
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllTasks/{userId}")
    public ResponseEntity<?> getAllTasks(@PathVariable Long userId) {
        try {
            List<Task> tasks = taskService.getAllTasks(userId);
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCompletedTasks/{userId}")
    public ResponseEntity<?> getCompletedTasks(@PathVariable Long userId) {
        try {
            List<Task> tasks = taskService.getCompletedTasks(userId);
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getTodayTasks/{userId}")
    public ResponseEntity<?> getTodayTasks(@PathVariable Long userId) {
        try {
            List<Task> tasks = taskService.getTodayTasks(userId);
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}