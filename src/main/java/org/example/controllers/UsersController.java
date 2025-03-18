package org.example.controllers;

import jakarta.validation.Valid;
import org.example.DTO.AddUserRequest;
import org.example.DTO.GetUserRequest;
import org.example.DTO.GetUserResponse;
import org.example.models.User;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UsersController {

    private final UserService userService;

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@Valid @RequestBody AddUserRequest addUserRequest) {
        try {
            User user = new User();
            user.setLogin(addUserRequest.getLogin());
            user.setPassword(addUserRequest.getPassword());
            user.setName(addUserRequest.getName());
            User createdUser = userService.addUser(user);
            return new ResponseEntity<>(createdUser.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @PostMapping("/getUser")
    public ResponseEntity<?> getUser(@Valid @RequestBody GetUserRequest getUserRequest) {
        try {
            Optional<User> optionalUser = userService.getUserByLoginAndPassword(
                    getUserRequest.getLogin(), getUserRequest.getPassword());

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                GetUserResponse getUserResponse = new GetUserResponse();
                getUserResponse.setUser_id(user.getId());
                getUserResponse.setName(user.getName());
                return new ResponseEntity<>(getUserResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Пользователь не найден", HttpStatus.NOT_FOUND); // 404
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Код 204
        } catch (Exception e) {
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }
}
