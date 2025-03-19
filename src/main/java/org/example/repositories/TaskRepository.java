package org.example.repositories;

import jakarta.transaction.Transactional;
import org.example.enums.Status;
import org.example.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUserId(Long userId);

    List<Task> findByUserIdAndStatus(Long userId, Status status);

    List<Task> findByUserIdAndDateAndStatus(Long userId, LocalDate date, Status status);

    Optional<Task> findByIdAndUserId(Long taskId, Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE Task t SET t.status = :status, t.completeDate = :completeDate, t.completeTime = :completeTime WHERE t.id = :taskId AND t.user.id = :userId")
    void completeTask(
            @Param("taskId") Long taskId,
            @Param("userId") Long userId,
            @Param("status") Status status,
            @Param("completeDate") LocalDate completeDate,
            @Param("completeTime") LocalTime completeTime
    );

    @Modifying
    @Transactional
    @Query("UPDATE Task t SET t.status = :status, t.completeDate = null, t.completeTime = null WHERE t.id = :taskId AND t.user.id = :userId")
    void returnCompletedTask(
            @Param("taskId") Long taskId,
            @Param("userId") Long userId,
            @Param("status") Status status
    );

    @Modifying
    @Transactional
    @Query("DELETE FROM Task t WHERE t.user.id = :userId AND t.status = :status")
    void deleteCompletedTasks(@Param("userId") Long userId, @Param("status") Status status);

    @Modifying
    @Transactional
    @Query("DELETE FROM Task t WHERE t.user.id = :userId AND t.id = :taskId")
    void deleteTask(@Param("userId") Long userId, @Param("taskId") Long taskId);

}