package org.example.DAO.Users;

import org.example.DataBase;
import org.example.SpringConfig;
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
    public int addUser(String login, String password, String name) {
        User user = SpringConfig.getContext().getBean("user", User.class);
        user.setLogin(login);
        user.setName(name);
        user.setPassword(password);
        return dataBase.addUser(user);
    }

    @Override
    public String deleteUser(int id) {
        return dataBase.deleteUser(id);
    }

    @Override
    public User getUser(String login, String password) {
        return dataBase.getUser(login, password);
    }
}
