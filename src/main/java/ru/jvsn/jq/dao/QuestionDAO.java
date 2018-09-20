package ru.jvsn.jq.dao;

import ru.jvsn.jq.interfaces.QuestionDAOi;
import ru.jvsn.jq.objects.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO implements QuestionDAOi{
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;


    @Override
    public List<Question> getAllQuestions() {
        List<Question> allQuestionsList = new ArrayList<>();
        connection = null;
        preparedStatement = null;

        try {
            connection = new ConnectionDAO().getConnect();
            preparedStatement = connection.prepareStatement("SELECT id, question FROM questions");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Question question = new Question();
                question.setId(resultSet.getInt(1));
                question.setQuestion(resultSet.getString(2));

                allQuestionsList.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allQuestionsList;
    }
}
