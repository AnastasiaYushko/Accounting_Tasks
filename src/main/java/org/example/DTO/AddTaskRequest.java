package org.example.DTO;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Data
@Scope("prototype")
public class AddTaskRequest {
    private int user_id;
    private String title;
    private String date;
    private String time;
}
