package org.example.DTO;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Data
@Scope("prototype")
public class ChangeTaskRequest {
    private int user_id;
    private int task_id;
    private String title;
    private String date;
    private String time;
}
