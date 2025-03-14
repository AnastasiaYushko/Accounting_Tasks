package org.example.DTO;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Data
@Scope("prototype")
public class GetCompletedTasksResponse {
    private ArrayList<String> tasks;
}
