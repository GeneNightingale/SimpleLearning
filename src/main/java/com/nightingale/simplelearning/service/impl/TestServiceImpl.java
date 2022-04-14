package com.nightingale.simplelearning.service.impl;

import com.nightingale.simplelearning.dao.TestDAO;
import com.nightingale.simplelearning.model.Test;
import com.nightingale.simplelearning.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    private TestDAO testDAO;

    @Autowired
    public void setTestDAO(TestDAO testDAO) {
        this.testDAO = testDAO;
    }

    @Override
    public Test getTestById(BigInteger id) {
        return testDAO.getTestById(id);
    }

    @Override
    public List<Test> getAllTestsByCourseId(BigInteger courseId) {
        return testDAO.getAllTestsByCourseId(courseId);
    }

    @Override
    public List<Test> getAllTests() {
        return testDAO.getAllTests();
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

