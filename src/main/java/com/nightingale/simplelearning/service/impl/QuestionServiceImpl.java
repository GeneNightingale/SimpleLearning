package com.nightingale.simplelearning.service.impl;

import com.nightingale.simplelearning.dao.QuestionDAO;
import com.nightingale.simplelearning.model.Question;
import com.nightingale.simplelearning.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private QuestionDAO questionDAO;

    @Autowired
    public void setQuestionDAO(QuestionDAO questionDAO) {
        this.questionDAO = questionDAO;
    }

    @Override
    public Question getQuestionById(BigInteger id) {
        return questionDAO.getQuestionById(id);
    }

    @Override
    public List<Question> getAllQuestionsByTestId(BigInteger testId) {
        return questionDAO.getAllQuestionsByTestId(testId);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionDAO.getAllQuestions();
    }

    @Override
    public boolean save(Question question, BigInteger testId) {
        return questionDAO.save(question, testId);
    }

    @Override
    public boolean delete(BigInteger id) {
        return questionDAO.delete(id);
    }

    @Override
    public boolean update(BigInteger id, Question newQuestion) {
        return questionDAO.update(id, newQuestion);
    }
}
