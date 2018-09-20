package ru.jvsn.jq.dao;

import ru.jvsn.jq.interfaces.ConnectionDAOi;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDAO implements ConnectionDAOi {
    private Connection connection = null;

    public Connection getConnect() {
        connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite::resource:jqBase.db");
            //connection = DriverManager.getConnection("jdbc:sqlite:D://jqBase.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
