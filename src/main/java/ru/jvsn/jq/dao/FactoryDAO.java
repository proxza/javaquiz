package ru.jvsn.jq.dao;

import ru.jvsn.jq.interfaces.FactoryDAOi;

public class FactoryDAO implements FactoryDAOi {
    private static FactoryDAO factory;

    private FactoryDAO() {
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("JDBC driver loading successfully!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static FactoryDAO getInstance() {
        if (factory == null) {
            factory = new FactoryDAO();
        }
        return factory;
    }

    public UserDAO getUserDAO() {
        return new UserDAO();
    }

    @Override
    public QuestionDAO getQuestionDAO() {
        return new QuestionDAO();
    }

    @Override
    public AnswerDAO getAnswerDAO() {
        return new AnswerDAO();
    }
}
