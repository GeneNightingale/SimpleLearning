package com.nightingale.simplelearning.service.impl;

import com.nightingale.simplelearning.dao.AnswerDAO;
import com.nightingale.simplelearning.model.Answer;
import com.nightingale.simplelearning.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerDAO answerDAO;

    @Override
    public Answer getAnswerById(BigInteger id) {
        return answerDAO.getAnswerById(id);
    }

    @Override
    public List<Answer> getAllAnswersByQuestionId(BigInteger questionId) {
        return answerDAO.getAllAnswersByQuestionId(questionId);
    }

    @Override
    public List<Answer> getAllAnswers() {
        return answerDAO.getAllAnswers();
    }

    @Override
    public boolean save(Answer answer, BigInteger questionId) {
        return answerDAO.save(answer, questionId);
    }

    @Override
    public boolean delete(BigInteger id) {
        return answerDAO.delete(id);
    }

    @Override
    public boolean update(BigInteger id, Answer newAnswer) {
        return answerDAO.update(id, newAnswer);
    }
}
