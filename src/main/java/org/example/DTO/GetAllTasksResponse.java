package org.example.DTO;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@Data
@Scope("prototype")
public class GetAllTasksResponse {
    private Long id;
    private String title;
    private LocalDate date;
    private LocalTime time;
}
