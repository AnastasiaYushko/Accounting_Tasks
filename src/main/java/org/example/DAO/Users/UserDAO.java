package org.example.DAO.Users;

import org.example.models.User;

public interface UserDAO {
    String addUser(String login, String password, String name);

    String deleteUser(int id);

    User getUser(String login,String password);
}
