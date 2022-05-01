package com.nightingale.simplelearning.dao;

import com.nightingale.simplelearning.model.Question;

import java.math.BigInteger;
import java.util.List;

public interface QuestionDAO {

    static final String SELECT_QUESTION_BY_ID = "SELECT * FROM question WHERE questionId = ?";

    static final String SELECT_QUESTIONS_BY_TEST_ID = "SELECT * FROM question WHERE testId = ?";

    static final String SELECT_ALL_QUESTIONS = "SELECT * FROM question";

    static final String INSERT_QUESTION_TO_TEST = "INSERT INTO question (questionNum, text, answer, testId) " +
            "VALUES (?, ?, ?, ?)";

    static final String DELETE_QUESTION_BY_ID = "DELETE FROM question WHERE questionId = ?";

    static final String DELETE_QUESTIONS_BY_TEST_ID = "DELETE FROM question WHERE testId = ?";

    static final String UPDATE_QUESTION_BY_ID = "UPDATE question " +
            "SET questionNum = ?, text = ?, answer = ? " +
            "WHERE questionId = ?;";

    Question getQuestionById(BigInteger id);
    List<Question> getAllQuestionsByTestId(BigInteger testId);
    List<Question> getAllQuestions();
    boolean deleteAllQuestionsByTestId(BigInteger id);
    boolean save(Question question, BigInteger testId);
    boolean delete(BigInteger id);
    boolean update(BigInteger id, Question newQuestion);

}
