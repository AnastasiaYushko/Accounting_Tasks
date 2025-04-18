package org.example.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GetTodayTasksResponse {
    private Long id;
    private String title;
    private LocalDate date;
    private String time;
}
