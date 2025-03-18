package org.example.models;

import lombok.*;
import org.example.enums.Status;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Getter
@Setter
@Component
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Scope("prototype")
public class Task {
    private int id;
    private String title;
    private String date;
    private String time;
    private Status status;
    private int user_id;
    private String complete_date;
    private String complete_time;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && user_id == task.user_id && Objects.equals(title, task.title) && Objects.equals(date, task.date) && Objects.equals(time, task.time) && status == task.status && Objects.equals(complete_date, task.complete_date) && Objects.equals(complete_time, task.complete_time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, date, time, status, user_id, complete_date, complete_time);
    }
}
