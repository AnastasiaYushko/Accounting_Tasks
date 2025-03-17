package org.example.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Data
@Scope("prototype")
public class AddTaskRequest {
    @Positive(message = "id не может быть <=0")
    private int user_id;
    @NotBlank(message = "Название задачи не может быть пустой")
    private String title;
    @NotBlank(message = "Дата не может быть пустой")
    private String date;
    @NotBlank(message = "Время не может быть пустым")
    private String time;
}
