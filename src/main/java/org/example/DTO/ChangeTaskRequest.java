package org.example.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@Data
@Scope("prototype")
public class ChangeTaskRequest {
    @Positive(message = "id не может быть <=0")
    private Long user_id;
    @Positive(message = "id не может быть <=0")
    private Long task_id;
    @NotBlank(message = "Название задачи не может быть пустой")
    private String title;
    @NotNull(message = "Дата не может быть пустой")
    private LocalDate date;
    @NotNull(message = "Время не может быть пустым")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;
}
