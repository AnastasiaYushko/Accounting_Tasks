package org.example.models;

import jakarta.persistence.Column;
import lombok.*;
import org.example.enums.Status;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@Setter
@Component
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Scope("prototype")
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Автоинкремент
    private Long id; // Используем Long, так как id может быть большим

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "date")
    private LocalDate date; // Используем LocalDate

    @Column(name = "time")
    private LocalTime time; // Используем LocalTime

    @Column(name = "complete_date")
    private LocalDate completeDate; // Используем LocalDate

    @Column(name = "complete_time")
    private LocalTime completeTime; // Используем LocalTime

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Связь с таблицей "user"

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status; // Используем enum Status

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(title, task.title) && Objects.equals(date, task.date) && Objects.equals(time, task.time) && Objects.equals(completeDate, task.completeDate) && Objects.equals(completeTime, task.completeTime) && Objects.equals(user, task.user) && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, date, time, completeDate, completeTime, user, status);
    }
}
