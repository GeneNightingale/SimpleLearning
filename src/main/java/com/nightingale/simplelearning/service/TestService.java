package com.nightingale.simplelearning.service;

import com.nightingale.simplelearning.model.Test;

import java.math.BigInteger;
import java.util.List;

public interface TestService {
    Test getTestById(BigInteger id);
    List<Test> getAllTestsByCourseId(BigInteger testId);
    List<Test> getAllTests();
    //boolean deleteAllTestsByCourseId(BigInteger id);
    boolean save(Test test, BigInteger courseId);
    boolean delete(BigInteger id);
    boolean update(BigInteger id, Test newTest);
}
