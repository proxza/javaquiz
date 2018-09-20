package ru.jvsn.jq.dao;

import ru.jvsn.jq.interfaces.AnswerDAOi;
import ru.jvsn.jq.objects.Answer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnswerDAO implements AnswerDAOi {
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;

    @Override
    public List<Answer> getAllAnswerForQuestion(int questionId) {
        List<Answer> allAnswers = new ArrayList<>();
        connection = null;
        preparedStatement = null;

        try {
            connection = new ConnectionDAO().getConnect();
            preparedStatement = connection.prepareStatement("SELECT id, answer, kind, point FROM answers WHERE qid = '" + questionId + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Answer answer = new Answer();
                answer.setId(resultSet.getInt(1));
                answer.setAnswer(resultSet.getString(2));
                answer.setKind(resultSet.getInt(3));
                answer.setPoint(resultSet.getInt(4));

                allAnswers.add(answer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allAnswers;
    }

    public Answer getTrueAnswerId(String id) {
        connection = null;
        preparedStatement = null;
        Answer answer = new Answer();

        try {
            connection = new ConnectionDAO().getConnect();
            preparedStatement = connection.prepareStatement("SELECT id, answer, kind, point FROM answers WHERE id = '" + id + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                answer.setId(resultSet.getInt(1));
                answer.setAnswer(resultSet.getString(2));
                answer.setKind(resultSet.getInt(3));
                answer.setPoint(resultSet.getInt(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answer;
    }
}
