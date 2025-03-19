package org.example.DTO;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Data
@Scope("prototype")
public class GetCompletedTasksResponse {
    private Long id;
    private String title;
    private LocalDate date;
    private String time;
    private LocalDate completeDate;
    private String completeTime;
}
