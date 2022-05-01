package com.nightingale.simplelearning.service.impl;

import com.nightingale.simplelearning.controller.request.RequestTest;
import com.nightingale.simplelearning.dao.TestDAO;
import com.nightingale.simplelearning.model.Question;
import com.nightingale.simplelearning.model.Result;
import com.nightingale.simplelearning.model.Test;
import com.nightingale.simplelearning.model.User;
import com.nightingale.simplelearning.service.QuestionService;
import com.nightingale.simplelearning.service.ResultService;
import com.nightingale.simplelearning.service.TestService;
import com.nightingale.simplelearning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestDAO testDAO;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ResultService resultService;

    @Autowired
    private UserService userService;

    @Override
    public Test getTestById(BigInteger id) {
        User currentUser = userService.getCurrentUser();
        switch (currentUser.getRole().toString()) {
            case "STUDENT":
                return getTestByIdNoAnswer(id);
            case "TEACHER":
                return testDAO.getTestById(id);
            default:
                return null;
        }
    }

    //Prevents test answers from being accessible to students
    @Override
    public Test getTestByIdNoAnswer(BigInteger id) {
        Test test = testDAO.getTestById(id);
        List<Question> questions = test.getQuestions();
        questions.forEach(question -> question.setAnswer(""));
        test.setQuestions(questions);

        Result result = resultService.getAllResultsByStudentAndTestId(
                BigInteger.valueOf(userService.getCurrentUser().getUserId()),
                BigInteger.valueOf(test.getTestId())
        );

        if (result != null) {
            test.setPassed(true);
            test.setGrade(result.getScore());
        } else
            test.setPassed(false);

        return test;
    }

    @Override
    public List<Test> getAllTestsByCourseId(BigInteger courseId) {
        User currentUser = userService.getCurrentUser();
        switch (currentUser.getRole().toString()) {
            case "STUDENT":
                List<Test> tests = testDAO.getAllTestsByCourseId(courseId);
                CollectionUtils.filter(tests, test -> ((Test) test).isPublic());
                for (Test test : tests) {
                    Result result = resultService.getAllResultsByStudentAndTestId(BigInteger.valueOf(currentUser.getUserId()), BigInteger.valueOf(test.getTestId()));
                    if (result != null) {
                        test.setPassed(true);
                        test.setGrade(result.getScore());
                    } else
                        test.setPassed(false);
                }
                return tests;
            case "TEACHER":
                return testDAO.getAllTestsByCourseId(courseId);
            default:
                return null;
        }
    }

    @Override
    public List<Test> getAllTests() {
        return testDAO.getAllTests();
    }

    //Calculates student's test result and writes it down in DB.results
    @Override
    public boolean completeTest(RequestTest requestTest, BigInteger testId) {
        Test test = getTestById(testId);
        List<Question> questions = test.getQuestions();                    //Questions
        BigDecimal questionsAmount = BigDecimal.valueOf(questions.size()); //How Many Questions in Total
        List<Question> answers = requestTest.getAnswers();                 //Given answers

        int correctAnswersAmount = 0; //How many student got right

        for (Question answer : answers) {           //For every answer given
            long answerId = answer.getQuestionId(); //find the appropriate question
            Question actualQuestion = questionService.getQuestionById(BigInteger.valueOf(answerId));
            if (actualQuestion != null) {           //And compare the answers
                if (answer.getAnswer().equals(actualQuestion.getAnswer()))
                    correctAnswersAmount++;         //If it's right, ++
            }
        }

        BigDecimal result = BigDecimal.valueOf(correctAnswersAmount);                               //How many student got right
        BigDecimal totalPercentage = result.divide(questionsAmount, 2, RoundingMode.HALF_UP); //Divide by total

        Result testResult = new Result();                                       //Create new result
        testResult.setScore(totalPercentage.multiply(BigDecimal.valueOf(100))); //Set score to percentage
        testResult.setTestId(testId.intValue());                                //Set testId to the one given
        testResult.setStudentId(userService.getCurrentUser().getUserId());      //Set studentId to the current one
        return resultService.save(testResult);
    }

    @Override
    public boolean makePublic(BigInteger testId) {
        return testDAO.makePublic(testId);
    }

    @Override
    public boolean makePrivate(BigInteger testId) {
        return testDAO.makePrivate(testId);
    }

    @Override
    public boolean save(Test test, BigInteger courseId) {
        return testDAO.save(test, courseId);
    }

    @Override
    public boolean delete(BigInteger id) {
        return testDAO.delete(id);
    }

    @Override
    public boolean update(BigInteger id, Test test) {
        return testDAO.update(id, test);
    }
}

