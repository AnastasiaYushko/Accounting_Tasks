package org.example.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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

import java.util.ArrayList;
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
                task.setUser(user);
            } else {
                return new ResponseEntity<>("Пользователь не найден", HttpStatus.NOT_FOUND); //404
            }
            Task addedTask = taskService.addTask(task);
            return new ResponseEntity<>(addedTask.getId(), HttpStatus.CREATED); //201
        } catch (Exception e) {
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); //500
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
                return new ResponseEntity<>("Задача не найдена или не принадлежит пользователю", HttpStatus.NOT_FOUND); //404
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }

    @PutMapping("/completeTask/{taskId}/{userId}")
    public ResponseEntity<?> completeTask(@PathVariable @Min(value = 0, message = "ID задачи не может быть <0") Long taskId, @PathVariable @Min(value = 0, message = "ID пользователя не может быть <0") Long userId) {
        try {
            if (userService.getUserById(userId).isEmpty()) {
                return new ResponseEntity<>("Пользователь не найден", HttpStatus.NOT_FOUND); //404
            }
            taskService.completeTask(taskId, userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //204
        } catch (Exception e) {
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }

    @PutMapping("/returnCompletedTask/{taskId}/{userId}")
    public ResponseEntity<?> returnCompletedTask(@PathVariable @Min(value = 0, message = "ID задачи не может быть <0") Long taskId, @PathVariable @Min(value = 0, message = "ID пользователя не может быть <0") Long userId) {
        try {
            if (userService.getUserById(userId).isEmpty()) {
                return new ResponseEntity<>("Пользователь не найден", HttpStatus.NOT_FOUND); //404
            }
            taskService.returnCompletedTask(taskId, userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //204
        } catch (Exception e) {
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteCompletedTasks/{userId}")
    public ResponseEntity<?> deleteCompletedTasks(@PathVariable @Min(value = 0, message = "ID пользователя не может быть <0") Long userId) {
        try {
            if (userService.getUserById(userId).isEmpty()) {
                return new ResponseEntity<>("Пользователь не найден", HttpStatus.NOT_FOUND); //404
            }
            taskService.deleteCompletedTasks(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //204
        } catch (Exception e) {
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteTask/{taskId}/{userId}")
    public ResponseEntity<?> deleteTask(@PathVariable @Min(value = 0, message = "ID задачи не может быть <0") Long taskId, @PathVariable @Min(value = 0, message = "ID пользователя не может быть <0") Long userId) {
        try {
            if (userService.getUserById(userId).isEmpty()) {
                return new ResponseEntity<>("Пользователь не найден", HttpStatus.NOT_FOUND); //404
            }
            taskService.deleteTask(taskId, userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //204
        } catch (Exception e) {
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllTasks/{userId}")
    public ResponseEntity<?> getAllTasks(@PathVariable @Min(value = 0, message = "ID пользователя не может быть <0") Long userId) {
        try {
            if (userService.getUserById(userId).isEmpty()) {
                return new ResponseEntity<>("Пользователь не найден", HttpStatus.NOT_FOUND); //404
            }
            List<Task> tasks = taskService.getAllTasks(userId);
            List<GetAllTasksResponse> listResult = new ArrayList<>();
            for (Task task : tasks){
                GetAllTasksResponse getAllTasksResponse = new GetAllTasksResponse();
                getAllTasksResponse.setId(task.getId());
                getAllTasksResponse.setTitle(task.getTitle());
                getAllTasksResponse.setDate(task.getDate());
                getAllTasksResponse.setTime(task.getTime());
                listResult.add(getAllTasksResponse);
            }
            return new ResponseEntity<>(listResult, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCompletedTasks/{userId}")
    public ResponseEntity<?> getCompletedTasks(@PathVariable @Min(value = 0, message = "ID пользователя не может быть <0") Long userId) {
        try {
            if (userService.getUserById(userId).isEmpty()) {
                return new ResponseEntity<>("Пользователь не найден", HttpStatus.NOT_FOUND); //404
            }
            List<Task> tasks = taskService.getCompletedTasks(userId);
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getTodayTasks/{userId}")
    public ResponseEntity<?> getTodayTasks(@PathVariable @Min(value = 0, message = "ID пользователя не может быть <0") Long userId) {
        try {
            if (userService.getUserById(userId).isEmpty()) {
                return new ResponseEntity<>("Пользователь не найден", HttpStatus.NOT_FOUND); //404
            }
            List<Task> tasks = taskService.getTodayTasks(userId);
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}