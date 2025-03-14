package org.example.DAO.Users;

import org.example.models.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {
    @Override
    public String addUser(String login, String password, String name) {
        return "";
    }

    @Override
    public String deleteUser(int id) {
        return "";
    }

    @Override
    public User getUser(int id) {
        return null;
    }
}
