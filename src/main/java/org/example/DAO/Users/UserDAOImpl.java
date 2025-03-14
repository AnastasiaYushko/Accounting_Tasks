package org.example.DAO.Users;

import org.example.DataBase;
import org.example.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private final DataBase dataBase;

    public UserDAOImpl(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public String addUser(String login, String password, String name) {
        return "";
    }

    @Override
    public String deleteUser(int id) {
        return "";
    }

    @Override
    public User getUser(String login,String password) {
        return null;
    }
}
