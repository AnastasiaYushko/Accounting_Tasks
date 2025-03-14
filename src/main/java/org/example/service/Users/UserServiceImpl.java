package org.example.service.Users;

import org.example.DTO.AddUserRequest;
import org.example.DTO.DeleteUserRequest;
import org.example.DTO.GetUserRequest;
import org.example.DTO.GetUserResponse;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public String addUser(AddUserRequest addUserRequest) {
        return "";
    }

    @Override
    public String deleteUser(DeleteUserRequest deleteUserRequest) {
        return "";
    }

    @Override
    public GetUserResponse getUser(GetUserRequest getUserRequest) {
        return null;
    }
}
