package org.example.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.repositories.TaskRepository;
import org.example.enums.Status;
import org.example.models.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    public Optional<Task> changeTask(Long userId, Long taskId, String title, LocalDate date, LocalTime time) {
        return taskRepository.findById(taskId)
                .map(existingTask -> {
                    if (!existingTask.getUser().getId().equals(userId)) {
                        return null;
                    }

                    Task updatedTask = new Task();
                    updatedTask.setId(taskId);
                    updatedTask.setUser(existingTask.getUser());
                    updatedTask.setTitle(title);
                    updatedTask.setDate(date);
                    updatedTask.setTime(time);
                    updatedTask.setCompleteDate(existingTask.getCompleteDate());
                    updatedTask.setCompleteTime(existingTask.getCompleteTime());
                    updatedTask.setStatus(existingTask.getStatus());

                    if (existingTask.equals(updatedTask)) {
                        return null;
                    }

                    existingTask.setTitle(title);
                    existingTask.setDate(date);
                    existingTask.setTime(time);

                    return taskRepository.save(existingTask);
                });
    }

    @Transactional
    public void completeTask(Long taskId, Long userId) {
        taskRepository.completeTask(taskId, userId, Status.completed, LocalDate.now(), LocalTime.now());
    }

    @Transactional
    public void returnCompletedTask(Long taskId, Long userId) {
        taskRepository.returnCompletedTask(taskId, userId, Status.active);
    }

    @Transactional
    public void deleteCompletedTasks(Long userId) {
        taskRepository.deleteCompletedTasks(userId, Status.completed);
    }

    @Transactional
    public void deleteTask(Long taskId, Long userId) {
        taskRepository.deleteTask(userId, taskId);
    }

    public List<Task> getAllTasks(Long userId) {
        return taskRepository.findByUserIdAndStatus(userId, Status.active);
    }

    public List<Task> getCompletedTasks(Long userId) {
        return taskRepository.findByUserIdAndStatus(userId, Status.completed);
    }

    public List<Task> getTodayTasks(Long userId) {
        return taskRepository.findByUserIdAndDateAndStatus(userId, LocalDate.now(), Status.active);
    }

    public Optional<Task> getTaskById(Long taskId) {
        return taskRepository.findById(taskId);
    }

    public Optional<Task> getTaskByUserId(Long userId, Long taskId) {
        return taskRepository.findByIdAndUserId(taskId, userId);
    }
}
