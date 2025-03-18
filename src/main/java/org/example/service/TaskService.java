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

    // Добавление нового таска
    public Task addTask(Task task) {
        // Валидация task (например, title не null и не пустой)
        return taskRepository.save(task);
    }

    // Изменение таска
    public Optional<Task> changeTask(Long userId, Long taskId, String title, LocalDate date, LocalTime time) {
        return taskRepository.findById(taskId)
                .map(task -> {
                    if (task.getUser().getId().equals(userId)) { // Проверяем, что задача принадлежит пользователю
                        task.setTitle(title);
                        task.setDate(date);
                        task.setTime(time);
                        return taskRepository.save(task);
                    } else {
                        return null; // Или выбросить исключение, если задача не принадлежит пользователю
                    }
                });
    }

    // Выполнение таска (то есть меняем его статус и добавляем дату и время завершения)
    @Transactional // Важно для операций UPDATE
    public void completeTask(Long taskId, Long userId) {
        taskRepository.completeTask(taskId, userId, Status.completed, LocalDate.now(), LocalTime.now());
    }

    // Меняем статус с completed на active
    @Transactional // Важно для операций UPDATE
    public void returnCompletedTask(Long taskId, Long userId) {
        taskRepository.returnCompletedTask(taskId, userId, Status.active);
    }

    // Удаление всех тасков пользователя у который статус complete
    @Transactional // Важно для операций DELETE
    public void deleteCompletedTasks(Long userId) {
        taskRepository.deleteCompletedTasks(userId, Status.completed);
    }

    // Удаляем таск
    @Transactional // Важно для операций DELETE
    public void deleteTask(Long taskId, Long userId) {
        taskRepository.deleteTask(userId, taskId);
    }

    // Получение всех тасков пользователя
    public List<Task> getAllTasks(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    // Получить все таски со статусом completed у пользователя
    public List<Task> getCompletedTasks(Long userId) {
        return taskRepository.findByUserIdAndStatus(userId, Status.completed);
    }

    // Получить все таски пользователя у которых date совпадает с сегодняшней
    public List<Task> getTodayTasks(Long userId) {
        return taskRepository.findByUserIdAndDate(userId, LocalDate.now());
    }

    public Optional<Task> getTaskById(Long taskId) {
        return taskRepository.findById(taskId);
    }
}
