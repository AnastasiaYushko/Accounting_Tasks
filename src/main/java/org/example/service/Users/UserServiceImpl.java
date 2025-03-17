package org.example.service.Users;

import org.example.DAO.Users.UserDAOImpl;
import org.example.DTO.AddUserRequest;
import org.example.DTO.DeleteUserRequest;
import org.example.DTO.GetUserRequest;
import org.example.DTO.GetUserResponse;
import org.example.SpringConfig;
import org.example.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAOImpl userDAO;

    @Autowired
    public UserServiceImpl(UserDAOImpl userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public int addUser(AddUserRequest addUserRequest) {
        return userDAO.addUser(addUserRequest.getLogin(),addUserRequest.getPassword(),addUserRequest.getName());
    }

    @Override
    public String deleteUser(DeleteUserRequest deleteUserRequest) {
        return userDAO.deleteUser(deleteUserRequest.getUser_id());
    }

    @Override
    public GetUserResponse getUser(GetUserRequest getUserRequest) {
        GetUserResponse getUserResponse = SpringConfig.getContext().getBean("getUserResponse", GetUserResponse.class);
        User user = userDAO.getUser(getUserRequest.getLogin(),getUserRequest.getPassword());
        getUserResponse.setUser_id(user.getId());
        getUserResponse.setName(user.getName());
        return getUserResponse;
    }
}
