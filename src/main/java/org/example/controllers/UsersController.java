package org.example.controllers;

import jakarta.validation.Valid;
import org.example.DTO.AddUserRequest;
import org.example.DTO.DeleteUserRequest;
import org.example.DTO.GetUserRequest;
import org.example.DTO.GetUserResponse;
import org.example.SpringConfig;
import org.example.service.Users.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = "*")
@Validated
public class UsersController {
    private final UserServiceImpl userService;

    @Autowired
    public UsersController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@Valid @RequestBody AddUserRequest addUserRequest) {
        try {
            int result = userService.addUser(addUserRequest);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getUser")
    public ResponseEntity<?> getUser(@RequestParam String login, @RequestParam String password ) {
        GetUserRequest getUserRequest = SpringConfig.getContext().getBean("getUserRequest", GetUserRequest.class);
        getUserRequest.setLogin(login);
        getUserRequest.setPassword(password);
        try {
            GetUserResponse getUserResponse = userService.getUser(getUserRequest);
            return new ResponseEntity<>(getUserResponse,HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestParam int user_id) {
        DeleteUserRequest deleteUserRequest = SpringConfig.getContext().getBean("deleteUserRequest",DeleteUserRequest.class);
        deleteUserRequest.setUser_id(user_id);
        try {
            String result = userService.deleteUser(deleteUserRequest);
            return new ResponseEntity<>(result,HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
