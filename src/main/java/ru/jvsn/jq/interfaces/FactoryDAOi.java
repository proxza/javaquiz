package ru.jvsn.jq.interfaces;

import ru.jvsn.jq.dao.AnswerDAO;
import ru.jvsn.jq.dao.QuestionDAO;
import ru.jvsn.jq.dao.UserDAO;

public interface FactoryDAOi {
    UserDAO getUserDAO();
    QuestionDAO getQuestionDAO();
    AnswerDAO getAnswerDAO();
}
