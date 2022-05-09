package com.nightingale.simplelearning.service.impl;

import com.nightingale.simplelearning.dao.ResultDAO;
import com.nightingale.simplelearning.dao.TestDAO;
import com.nightingale.simplelearning.model.Course;
import com.nightingale.simplelearning.model.Result;
import com.nightingale.simplelearning.model.Test;
import com.nightingale.simplelearning.model.User;
import com.nightingale.simplelearning.service.CourseService;
import com.nightingale.simplelearning.service.ResultService;
import com.nightingale.simplelearning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    private ResultDAO resultDAO;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TestDAO testDAO;

    @Override
    public Result getResultById(BigInteger id) {
        return resultDAO.getResultById(id);
    }

    @Override
    public List<Result> getAllResultsByTestId(BigInteger testId) {
        List<Result> results = new ArrayList<>();
        Test test = testDAO.getTestById(testId);
        List<User> students = courseService.getAllStudentsByCourseId(BigInteger.valueOf(test.getCourseId()));

        for (User user : students) {
            Result result = resultDAO.getAllResultsByStudentAndTestId(BigInteger.valueOf(user.getUserId()), testId);
            if (result == null) {
                Result falseResult = new Result();
                falseResult.setTitle(test.getTitle());
                falseResult.setName(user.getName());
                falseResult.setTestId(test.getTestId());
                falseResult.setStudentId(user.getUserId());
                falseResult.setScore(BigDecimal.valueOf(-1));
                results.add(falseResult);
            } else
                results.add(result);
        }
        return results;
    }

    @Override
    public List<Result> getAllResultsByCourseAndStudent(BigInteger courseId, BigInteger studentId) {
        List<Result> results = new ArrayList<>();
        Course course = courseService.getCourseById(courseId);
        List<Test> tests = course.getTests();
        User student = userService.getUserById(studentId);

        for (Test test : tests) {
            Result result = resultDAO.getAllResultsByStudentAndTestId(
                    BigInteger.valueOf(student.getUserId()),
                    BigInteger.valueOf(test.getTestId())
            );
            if (result == null) {
                Result falseResult = new Result();
                falseResult.setTitle(test.getTitle());
                falseResult.setName(student.getName());
                falseResult.setTestId(test.getTestId());
                falseResult.setStudentId(student.getUserId());
                falseResult.setScore(BigDecimal.valueOf(-1));
                results.add(falseResult);
            } else
                results.add(result);
        }
        return results;
    }

    @Override
    public List<Result> getAllResultsByStudentId(BigInteger studentId) {
        return resultDAO.getAllResultsByStudentId(studentId);
    }

    @Override
    public Result getAllResultsByStudentAndTestId(BigInteger studentId, BigInteger testId) {
        return resultDAO.getAllResultsByStudentAndTestId(studentId, testId);
    }

    @Override
    public List<Result> getAllResults() {
        return resultDAO.getAllResults();
    }

    @Override
    public boolean deleteAllResultsByTestId(BigInteger id) {
        return resultDAO.deleteAllResultsByTestId(id);
    }

    @Override
    public boolean saveResultByCurrentUser(Result result) {
        User currentUser = userService.getCurrentUser();
        if ("STUDENT".equals(currentUser.getRole().toString())) {
            result.setStudentId(currentUser.getUserId());
            return resultDAO.save(result);
        } else {
            return false;
        }
    }

    @Override
    public boolean save(Result result) {
        return resultDAO.save(result);
    }

    @Override
    public boolean delete(BigInteger id) {
        return resultDAO.delete(id);
    }

    @Override
    public boolean update(BigInteger id, Result newResult) {
        return resultDAO.update(id, newResult);
    }
}
