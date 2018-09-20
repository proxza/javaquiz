package ru.jvsn.jq.dao;

import ru.jvsn.jq.interfaces.UserDAOi;
import ru.jvsn.jq.objects.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAO implements UserDAOi{
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;

    @Override
    public void addUserToBase(User user) {
        //debug
        System.out.println("User: " + user.getName());
        System.out.println("Mistakes: " + user.getMistakes());
    }
}
