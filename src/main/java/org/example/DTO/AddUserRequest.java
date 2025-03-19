package org.example.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddUserRequest {
    @NotBlank(message = "Логин не может быть пустым")
    private String login;
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
    @NotBlank(message = "Имя не может быть пустым")
    private String name;
}
