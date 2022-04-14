package com.nightingale.simplelearning.service;

import com.nightingale.simplelearning.model.Answer;

import java.math.BigInteger;
import java.util.List;

public interface AnswerService {

    Answer getAnswerById(BigInteger id);

    List<Answer> getAllAnswersByQuestionId(BigInteger questionId);

    List<Answer> getAllAnswers();

    boolean save(Answer answer, BigInteger questionId);

    boolean delete(BigInteger id);

    boolean update(BigInteger id, Answer newAnswer);
}
