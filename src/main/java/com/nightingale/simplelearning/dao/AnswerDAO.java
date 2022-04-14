package com.nightingale.simplelearning.dao;

import com.nightingale.simplelearning.model.Answer;

import java.math.BigInteger;
import java.util.List;

public interface AnswerDAO {

    static final String SELECT_ANSWER_BY_ID = "SELECT * FROM answer WHERE answerId = ?";

    static final String SELECT_ANSWERS_BY_QUESTION_ID = "SELECT * FROM answer WHERE questionId = ?";

    static final String SELECT_ALL_ANSWERS = "SELECT * FROM answer";

    static final String INSERT_ANSWER_TO_QUESTION = "INSERT INTO answer (questionId, answer) " +
            "VALUES (?, ?)";

    static final String DELETE_ANSWER_BY_ID = "DELETE FROM answer WHERE answerId = ?";

    static final String DELETE_ANSWERS_BY_QUESTION_ID = "DELETE FROM answer WHERE questionId = ?";

    static final String UPDATE_ANSWER_BY_ID = "UPDATE answer " +
            "SET answer = ? " +
            "WHERE answerId = ?;";

    Answer getAnswerById(BigInteger id);
    List<Answer> getAllAnswersByQuestionId(BigInteger questionId);
    List<Answer> getAllAnswers();
    boolean deleteAllAnswersByQuestionId(BigInteger id);
    boolean save(Answer answer, BigInteger questionId);
    boolean delete(BigInteger id);
    boolean update(BigInteger id, Answer newAnswer);

}