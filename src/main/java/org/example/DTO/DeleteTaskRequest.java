package org.example.DTO;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Data
@Scope("prototype")
public class DeleteTaskRequest {
    private int user_id;
    private int task_id;
}
