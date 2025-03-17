package org.example.service.Users;

import org.example.DTO.AddUserRequest;
import org.example.DTO.DeleteUserRequest;
import org.example.DTO.GetUserRequest;
import org.example.DTO.GetUserResponse;

public interface UserService {
    int addUser(AddUserRequest addUserRequest);

    String deleteUser(DeleteUserRequest deleteUserRequest);

    GetUserResponse getUser(GetUserRequest getUserRequest);
}
