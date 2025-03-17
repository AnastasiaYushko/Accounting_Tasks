package org.example.DTO;

import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Data
@Scope("prototype")
public class ReturnCompletedTaskRequest {
    @Positive(message = "id не может быть <=0")
    private int user_id;
    @Positive(message = "id не может быть <=0")
    private int task_id;
}
