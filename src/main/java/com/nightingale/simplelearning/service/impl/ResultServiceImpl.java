package com.nightingale.simplelearning.service.impl;

import com.nightingale.simplelearning.dao.ResultDAO;
import com.nightingale.simplelearning.model.Result;
import com.nightingale.simplelearning.model.User;
import com.nightingale.simplelearning.service.ResultService;
import com.nightingale.simplelearning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    private ResultDAO resultDAO;

    @Autowired
    private UserService userService;

    @Override
    public Result getResultById(BigInteger id) {
        return resultDAO.getResultById(id);
    }

    @Override
    public List<Result> getAllResultsByTestId(BigInteger testId) {
        return resultDAO.getAllResultsByTestId(testId);
    }

    @Override
    public List<Result> getAllResultsByStudentId(BigInteger studentId) {
        return resultDAO.getAllResultsByStudentId(studentId);
    }

    @Override
    public List<Result> getAllResultsByStudentAndTestId(BigInteger studentId, BigInteger testId) {
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
