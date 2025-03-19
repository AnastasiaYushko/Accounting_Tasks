package org.example.models;

import jakarta.persistence.Column;
import lombok.*;
import org.example.enums.Status;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Автоинкремент
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private LocalTime time;

    @Column(name = "complete_date")
    private LocalDate completeDate;

    @Column(name = "complete_time")
    private LocalTime completeTime;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Override
    public boolean equals(Object o) {
        System.out.println("Equals called for Task with ID: " + this.id);
        if (this == o) {
            System.out.println("  Same object");
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            System.out.println("  Null or different class");
            return false;
        }
        Task task = (Task) o;
        System.out.println("  Comparing Task with ID: " + task.getId());
        return Objects.equals(id, task.id) &&
                Objects.equals(title, task.title) &&
                Objects.equals(date, task.date) &&
                Objects.equals(time, task.time) &&
                Objects.equals(completeDate, task.completeDate) &&
                Objects.equals(completeTime, task.completeTime) &&
                Objects.equals(getUser() != null ? getUser().getId() : null, task.getUser() != null ? task.getUser().getId() : null) &&
                status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, date, time, completeDate, completeTime,  getUser() != null ? getUser().getId() : null, status); // Хешируем по ID пользователя
    }
}