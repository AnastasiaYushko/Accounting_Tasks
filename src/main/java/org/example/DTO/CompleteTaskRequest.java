package org.example.DTO;

import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@Data
@Scope("prototype")
public class CompleteTaskRequest {
    @Positive(message = "id не может быть <=0")
    private Long user_id;
    @Positive(message = "id не может быть <=0")
    private Long task_id;
    private LocalDate complete_date;
    private LocalTime complete_time;
}
