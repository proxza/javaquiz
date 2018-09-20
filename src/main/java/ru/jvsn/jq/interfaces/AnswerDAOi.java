package ru.jvsn.jq.interfaces;

import ru.jvsn.jq.objects.Answer;

import java.util.List;

public interface AnswerDAOi {
    List<Answer> getAllAnswerForQuestion(int questionId);
}
