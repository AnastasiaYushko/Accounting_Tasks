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
                .map(task -> {
                    if (task.getUser().getId().equals(userId)) {
                        task.setTitle(title);
                        task.setDate(date);
                        task.setTime(time);
                        return taskRepository.save(task);
                    } else {
                        return null;
                    }
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
        return taskRepository.findByUserId(userId);
    }

    public List<Task> getCompletedTasks(Long userId) {
        return taskRepository.findByUserIdAndStatus(userId, Status.completed);
    }

    public List<Task> getTodayTasks(Long userId) {
        return taskRepository.findByUserIdAndDate(userId, LocalDate.now());
    }

    public Optional<Task> getTaskById(Long taskId) {
        return taskRepository.findById(taskId);
    }
}
