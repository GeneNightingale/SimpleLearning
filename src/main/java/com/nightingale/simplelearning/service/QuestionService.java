package com.nightingale.simplelearning.service;

import com.nightingale.simplelearning.model.Question;

import java.math.BigInteger;
import java.util.List;

public interface QuestionService {
    Question getQuestionById(BigInteger id);
    List<Question> getAllQuestionsByTestId(BigInteger testId);
    List<Question> getAllQuestions();
    boolean save(Question question, BigInteger testId);
    boolean delete(BigInteger id);
    boolean update(BigInteger id, Question newQuestion);
}
