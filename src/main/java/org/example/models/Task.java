package org.example.models;

import jakarta.persistence.*;
import lombok.*;
import org.example.enums.Status;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Component
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Scope("prototype")
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "date")
    private Date date;
    @Column(name = "time")
    private Time time;
    @Column(name = "status")
    private Status status;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(title, task.title) && Objects.equals(date, task.date) && Objects.equals(time, task.time) && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, date, time, status);
    }
}
